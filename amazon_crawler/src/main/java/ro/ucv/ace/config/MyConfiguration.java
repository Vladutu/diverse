package ro.ucv.ace.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages = "ro.ucv.ace.repository")
@EnableTransactionManagement
@ComponentScan("ro.ucv.ace")
public class MyConfiguration extends Neo4jConfiguration {

    @Bean
    public SessionFactory getSessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory("ro.ucv.ace.entity");
    }

    // needed for session in view in web-applications
    @Override
    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
