package edu.montana.gsoc.msusel.jpa

import org.hibernate.dialect.*

/**
 * @author Isaac Griffith
 * @version 1.3.0
 */
enum DatabaseProduct {

    DERBY(
            new DataSourceConfiguration() {
                @Override
                Map<String, String> configure(String connectionURL, String userName, String password) {
                    Map<String, String> props = [:]
                    props["javax.persistence.jdbc.driver"] = "org.apache.derby.jdbc.EmbeddedDriver"
                    props["javax.persistence.jdbc.url"] = connectionURL ? connectionURL : "jdbc:mariadb://localhost/tester"
                    props["javax.persistence.jdbc.user"] = userName
                    props["javax.persistence.jdbc.password"] = password
                    props["hibernate.dialect"] = DerbyTenSixDialect.class.getName()
                    props["hibernate.use_sql_comments"] = "true"
                    props["hibernate.hbm2ddl.auto"] = "create"
                    props["hibernate.show_sql"] = "true"
                    props["hibernate.format_sql"] = "true"
                    props
                }
            }

    ),
    MARIA(
            new DataSourceConfiguration() {
                @Override
                Map<String, String> configure(String connectionURL, String userName, String password) {
                    Map<String, String> props = [:]
                    props["javax.persistence.jdbc.driver"] = "org.mariadb.jdbc.Driver"
                    props["javax.persistence.jdbc.url"] = connectionURL ? connectionURL : "jdbc:mariadb://localhost/tester"
                    props["javax.persistence.jdbc.user"] = userName
                    props["javax.persistence.jdbc.password"] = password
                    props["hibernate.dialect"] = MariaDB53Dialect.class.getName()
                    props["hibernate.use_sql_comments"] = "true"
                    props["hibernate.hbm2ddl.auto"] = "create"
                    props["hibernate.show_sql"] = "true"
                    props["hibernate.format_sql"] = "true"
                    props
                }
            }
    ),
    H2(
            new DataSourceConfiguration() {
                @Override
                Map<String, String> configure(String connectionURL, String userName, String password) {
                    Map<String, String> props = [:]
                    props["javax.persistence.jdbc.driver"] = "org.h2.Driver"
                    props["javax.persistence.jdbc.url"] = connectionURL ? connectionURL : "jdbc:h2:mem:arctest"
                    props["javax.persistence.jdbc.user"] = userName
                    props["javax.persistence.jdbc.password"] = password
                    props["hibernate.dialect"] = H2Dialect.class.getName()
                    props["hibernate.use_sql_comments"] = "true"
                    props["hibernate.hbm2ddl.auto"] = "create"
                    props["hibernate.show_sql"] = "true"
                    props["hibernate.format_sql"] = "true"
                    props
                }
            },

    ),
    POSTGRESQL(
            new DataSourceConfiguration() {
                @Override
                Map<String, String> configure(String connectionURL, String userName, String password) {
                    Map<String, String> props = [:]
                    props["javax.persistence.jdbc.driver"] = "org.postgresql.Driver"
                    props["javax.persistence.jdbc.url"] = connectionURL ? connectionURL : "jdbc:mariadb://localhost/arctest"
                    props["javax.persistence.jdbc.user"] = userName
                    props["javax.persistence.jdbc.password"] = password
                    props["hibernate.dialect"] = PostgreSQL95Dialect.class.getName()
                    props["hibernate.use_sql_comments"] = "true"
                    props["hibernate.hbm2ddl.auto"] = "create"
                    props["hibernate.show_sql"] = "true"
                    props["hibernate.format_sql"] = "true"
                    props
                }
            }
    ),
    MYSQL(
            new DataSourceConfiguration() {
                @Override
                Map<String, String> configure(String connectionURL, String userName, String password) {
                    Map<String, String> props = [:]
                    props["javax.persistence.jdbc.driver"] = "com.mysql.cj.jdbc.Driver"
                    props["javax.persistence.jdbc.url"] = connectionURL ? connectionURL : "jdbc:mysql://localhost:3306/arctest"
                    props["javax.persistence.jdbc.user"] = userName
                    props["javax.persistence.jdbc.password"] = password
                    props["hibernate.dialect"] = MySQL57Dialect.class.getName()
                    props["hibernate.use_sql_comments"] = "true"
                    props["hibernate.hbm2ddl.auto"] = "create"
                    props["hibernate.show_sql"] = "true"
                    props["hibernate.format_sql"] = "true"
                    props
                }
            }
    )

    public DataSourceConfiguration configuration

    private DatabaseProduct(DataSourceConfiguration configuration) {
        this.configuration = configuration
    }

    interface DataSourceConfiguration {

        Map<String, String> configure(String connectionURL, String userName, String password)
    }

}