package pl.cyfronet.bazaar.engine.rules;

import com.agreemount.bean.Query;
import com.agreemount.bean.document.state.State;
import com.agreemount.bean.metric.Metric;
import com.agreemount.bean.metric.MetricCategory;
import com.agreemount.bean.query.QueryCategory;
import com.agreemount.slaneg.action.definition.Action;
import com.agreemount.slaneg.constraint.action.definition.ActionConstraint;
import com.agreemount.slaneg.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class YamlProvidersConfiguration {

    @Bean(name = "metricsYamlProvider")
    public GenericYamlProvider<Metric> metricGenericYamlProvider() {
        return new GenericYamlProvider<>("metrics");
    }

    @Bean(name = "metricCategoriesYamlProvider")
    public GenericYamlProvider<MetricCategory> metricCategoryGenericYamlProvider() {
        return new GenericYamlProvider<>("metricCategories");
    }

    @Bean(name = "documentRelationsQueriesYamlProvider")
    public GenericYamlProvider<Query> documentRelationsQueriesGenericYamlProvider() {
        return new GenericYamlProvider<>("documentRelationsQueries");
    }

    @Bean(name = "actionsYamlProvider")
    public GenericYamlProvider<Action> actionGenericYamlProvider() {
        return new GenericYamlProvider<>("actions");
    }

    @Bean(name = "queryCategoriesYamlProvider")
    public GenericYamlProvider<QueryCategory> queryCategoriesYamlProvider() {
        return new GenericYamlProvider<>("queryCategories");
    }

    @Bean(name = "queriesYamlProvider")
    public GenericYamlProvider<Query> queryGenericYamlProvider() {
        return new GenericYamlProvider<>("queries");
    }

    @Bean(name = "statesYamlProvider")
    public GenericYamlProvider<State> stateGenericYamlProvider() {
        return new GenericYamlProvider<>("states");
    }


    @Bean(name = "constraintsYamlProvider")
    public GenericYamlProvider<ActionConstraint> constraintGenericYamlProvider() {
        return new GenericYamlProvider<>("constraints");
    }

    @Bean(name = "messagesYamlProvider")
    public GenericYamlProvider<Message> messageGenericYamlProvider() {
        return new GenericYamlProvider<>("messages");
    }
}