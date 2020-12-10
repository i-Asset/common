package at.srfg.iot.common.solr.indexing.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import at.srfg.iot.common.solr.indexing.core.solr.SolrContext;


@Configuration
@EnableSolrRepositories(basePackages= {
			"at.srfg.iot.common.solr.indexing.core.service.repository"
		}, 
		schemaCreationSupport=true)
public class SolrIndexingContext extends SolrContext {
}
