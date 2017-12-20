package codetree.utils

import codetree.CodeTree
import codetree.INode
import codetree.ProjectNode
import codetree.node.FieldNode
import codetree.node.FileNode
import codetree.node.MethodNode
import codetree.node.ModuleNode
import codetree.node.NamespaceNode
import codetree.node.StatementNode
import codetree.node.TypeNode
import com.google.common.collect.Queues
import com.google.common.collect.Sets

class CodeTreeUtils {

    /**
     * The tree used for the various operations herein.
     */
    private final CodeTree tree

    /**
     * Extracts the code tree section from the root down to the provided node
     * and then everything below the node.
     *
     * @param node
     *            Node whose CodeTree is to be extracted.
     * @return The code tree extracted for the given node. or null if the node
     *         is null or not present in the tree.
     */
    def extractTree(INode node)
    {
        CodeTree retVal = null

        if (node instanceof ProjectNode)
        {
            retVal = node.extractTree(tree)
        }
        else if (node instanceof ModuleNode)
        {
            retVal = node.extractTree(tree)
        }
        else if (node instanceof FileNode)
        {
            retVal = node.extractTree(tree)
        }
        else if (node instanceof TypeNode)
        {
            retVal = node.extractTree(tree)
        }
        else if (node instanceof MethodNode)
        {
            retVal = node.extractTree(tree)
        }

        return retVal
    }

    /**
     * Searches for a file with the given qualified identifier in this CodeTree.
     *
     * @param qid
     *            Qualified identifier
     * @return The FileNode matching the given identifier, or null if the given
     *         identifier is null or empty or if no such file exists in the
     *         tree.
     */
    def findFile(final String qid)
    {
        if (qid == null || qid.isEmpty())
        {
            return null
        }

        for (ProjectNode p : getProjects())
        {
            if (p.hasFile(qid))
            {
                return p.getFile(qid)
            }
            else
            {
                for (ModuleNode m : p.getModules())
                {
                    if (m.hasFile(qid))
                        return m.getFile(qid)
                }
            }
        }
        return null
    }

    /**
     * Searches for a MethodNode with the given qualified identifier.
     *
     * @param identifier
     *            Qualified Identifier
     * @return MethodNode with matching qualified identifier, or null if the
     *         identifier is null, empty, or no such MethodNode exists.
     */
    def findMethod(final String identifier)
    {
        if (identifier == null || identifier.isEmpty())
            return null
        String[] ids = identifier.split("#")
        TypeNode tn = findType(ids[0])

        if (tn != null)
            return tn.getMethod(ids[1])

        return null
    }

    /**
     * Searches the CodeTree for a ModuleNode with the given qualified
     * identifier.
     *
     * @param qIdentifier
     *            Qualified Identifier
     * @return The ModuleNode with matching qualified identifier, or null if the
     *         provided qualified identifier is null or empty or no such
     *         matching ModuleNode exists.
     */
    def findModule(String qIdentifier)
    {
        if (qIdentifier == null || qIdentifier.isEmpty())
            return null

        Set<ProjectNode> projects = getProjects()

        for (ProjectNode p : projects)
        {
            if (p.hasModule(qIdentifier))
                return p.getModule(qIdentifier)
        }

        return null
    }

    /**
     * Given a node this method will search for the parent node using it's
     * parent identifier.
     *
     * @param node
     *            Node whose parent is to be found for.
     * @return A Node with matching qualified identifier to the given node's
     *         parent identifier, or null if the provided parent id is null or
     *         no such node exists in the code tree with the parent id.
     */
    def findParent(INode node)
    {
        INode parent = null
        if (node instanceof FieldNode)
        {
            parent = findType(node.getParentKey())
        }
        else if (node instanceof StatementNode)
        {
            parent = findMethod(node.getParentKey())
        }
        else if (node instanceof MethodNode)
        {
            parent = findType(node.getParentKey())
        }
        else if (node instanceof TypeNode)
        {
            parent = findFile(node.getParentKey())
        }
        else if (node instanceof FileNode)
        {
            parent = findProject(node.getParentKey())
            if (parent == null)
                parent = findModule(node.getParentKey())
        }
        else if (node instanceof ModuleNode || node instanceof NamespaceNode)
        {
            parent = findProject(node.getParentKey())
        }
        else if (node instanceof ProjectNode)
        {
            if (node.getParentKey() != null)
                parent = findProject(node.getParentKey())
        }

        return parent
    }

    /**
     * Searches the code tree for a project with a qualified identifier matching
     * the qualified identifier provided.
     *
     * @param qid
     *            Qualified Identifier
     * @return ProjectNode with matching qualified identifier as the one
     *         provided, or null if no such ProjectNode exists in the CodeTree
     *         or the provided identifier is null or empty.
     */
    public ProjectNode findProject(String qid)
    {
        if (qid == null || qid.isEmpty())
            return null

        Queue<ProjectNode> queue = Queues.newArrayDeque()

        if (this.tree.getProject() != null)
            queue.offer(this.tree.getProject())

        while (!queue.isEmpty())
        {
            ProjectNode node = queue.poll()
            if (node.getQIdentifier().equals(qid))
                return node

            for (ProjectNode pn : node.getSubProjects())
            {
                queue.offer(pn)
            }
        }

        return null
    }

    /**
     * Searches the CodeTree for a TypeNode whose qualified identifier matches
     * the one provided.
     *
     * @param key
     *            Qualified Identifier
     * @return TypeNode with matching qualified identifier to the one provided,
     *         or null if no such TypeNode exists in the CodeTree or the
     *         provided identifier is null or empty.
     */
    public TypeNode findType(final String key)
    {
        if (key == null || key.isEmpty())
            return null

        for (final TypeNode type : getTypes())
        {
            if (type.getQIdentifier().equals(key))
            {
                return type
            }
        }

        return null
    }

    /**
     * Searches the CodeTree for a FileNode whose qualified identifier matches
     * the one provided.
     *
     * @param file
     *            Qualified Identifier
     * @return FileNode with matching qualified identifier to the one provided,
     *         or null if no such FileNode exists in the CodeTree or the
     *         provided identifier is null or empty.
     */
    public FileNode getFile(final String file)
    {
        if (file == null || file.isEmpty())
        {
            return null
        }

        for (FileNode fn : getFiles())
        {
            if (fn.getQIdentifier().equals(file))
                return fn
        }

        return null
    }

    /**
     * @return The set of all files within the tree.
     */
    public Set<FileNode> getFiles()
    {
        Set<FileNode> files = Sets.newHashSet()

        Queue<ProjectNode> queue = Queues.newArrayDeque()

        queue.add(tree.getProject())

        while (!queue.isEmpty())
        {
            ProjectNode pn = queue.poll()
            files.addAll(pn.getFiles())
            queue.addAll(pn.getSubProjects())
        }

        return files
    }

    /**
     * @return The set of all methods within the tree.
     */
    public Set<MethodNode> getMethods()
    {
        final Set<MethodNode> methods = Sets.newHashSet()

        getTypes().forEach({type -> methods.addAll(type.getMethods())})

        return methods
    }


    /**
     * @return The set of all projects within the tree (including the root
     *         project)
     */
    public Set<ProjectNode> getProjects()
    {
        Set<ProjectNode> projects = Sets.newHashSet()
        Queue<ProjectNode> q = Queues.newArrayDeque()

        if (tree.getProject() != null)
            q.offer(tree.getProject())

        while (!q.isEmpty())
        {
            ProjectNode project = q.poll()
            projects.add(project)
            q.addAll(project.getSubProjects())
        }

        return projects
    }

    /**
     * @return The set of all known types in the tree.
     */
    public Set<TypeNode> getTypes()
    {
        final Set<TypeNode> types = Sets.newHashSet()

        getFiles().forEach({file -> types.addAll(file.getTypes())})

        return types
    }

    /**
     * Merges the CodeTree this class operates upon with the one provided.
     *
     * @param other
     *            CodeTree to merge into the currently operated on CodeTree.
     */
    void merge(CodeTree other)
    {
        if (other == null)
            return

        ProjectNode pn = other.getProject()

        if (tree.getProject() == null)
        {
            tree.setProject(pn)
        }
        if (pn.hasParent())
        {
            if (pn.getParentKey().equals(tree.getProject().getQIdentifier()))
            {
                tree.getProject().addSubProject(pn)
            }
        }
        else if (tree.getProject().equals(pn))
        {
            tree.getProject().update(pn)
        }
    }

    /**
     * Finds the file with the given identifier and removes it from its parent
     * in the CodeTree.
     *
     * @param file
     *            Qualified Identifier of the file to be removed.
     */
    void removeFile(final String file)
    {
        if (file == null || file.isEmpty())
        {
            return
        }

        INode parent = findParent(findFile(file))
        if (parent instanceof ProjectNode)
        {
            ((ProjectNode) parent).removeFile(file)
        }
        else if (parent instanceof ModuleNode)
        {
            ((ModuleNode) parent).removeFile(file)
        }
    }

    /**
     * Updates a file in the CodeTree using the provided file. This method first
     * searches the tree for a corresponding file matching the provided one. If
     * found the existing file is merged with the provided one. If no such file
     * exists, the new one is added to the tree. Finally, if the provided file
     * is null, nothing happens.
     *
     * @param node
     *            File to be used to update the tree.
     */
    synchronized void updateFile(final FileNode node)
    {
        if (node == null)
            return

        INode container
        container = findProject(node.getParentKey()) != null ? findProject(node.getParentKey()) : findModule(node.getParentKey())

        if (container instanceof ProjectNode)
        {
            if (((ProjectNode) container).getFile(node.getQIdentifier()) == null)
            {
                ((ProjectNode) container).addFile(node)
            }

            ((ProjectNode) container).getFile(node.getQIdentifier()).update(node)
        }
        else
        {
            if (((ModuleNode) container).getFile(node.getQIdentifier()) == null)
            {
                ((ModuleNode) container).addFile(node)
            }

            ((ModuleNode) container).getFile(node.getQIdentifier()).update(node)
        }
    }

    /**
     * Updates the root project of the CodeTree by merging it with the provided
     * project.
     *
     * @param node
     *            Project to merge into the root project of the tree.
     */
    synchronized void updateRootProject(final ProjectNode node)
    {
        if (node == null)
            return

        if (tree.getProject() == null)
            tree.setProject(node)
        else
            tree.getProject().update(node)
    }
}
