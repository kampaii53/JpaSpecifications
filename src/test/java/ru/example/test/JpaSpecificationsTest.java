package ru.example.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.example.common.SearchCriteria;
import ru.example.entities.ExampleEntity;
import ru.example.repositories.ExampleChildEntityRepository;
import ru.example.repositories.ExampleEntityRepository;
import ru.example.test.config.H2JpaConfiguration;
import ru.example.utils.JpaSpecificationsBuilder;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        H2JpaConfiguration.class
}
)
@ActiveProfiles("test")
@Transactional
public class JpaSpecificationsTest {

    @Autowired
    private ExampleEntityRepository exampleEntityRepository;

    @Autowired
    private ExampleChildEntityRepository exampleChildEntityRepository;

    private JpaSpecificationsBuilder<ExampleEntity> specificationsBuilder = new JpaSpecificationsBuilder<>();

    @Test
    public void testExecution(){
        ExampleEntity c = exampleEntityRepository.save(new ExampleEntity());
        SearchCriteria criteria = new SearchCriteria("id", SearchCriteria.SearchOperation.MORE,"0",null, SearchCriteria.JoinType.AND);

        assertEquals(1,exampleEntityRepository.findAll(specificationsBuilder.buildAndSpecification(Collections.singletonList(criteria))).size());
    }
}
