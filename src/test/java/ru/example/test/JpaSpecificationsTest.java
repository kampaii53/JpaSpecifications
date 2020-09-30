package ru.example.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.example.common.JoinType;
import ru.example.common.SearchCriteria;
import ru.example.common.SearchOperation;
import ru.example.entities.ExampleChildEntity;
import ru.example.entities.ExampleEntity;
import ru.example.entities.ExampleSecondChildEntity;
import ru.example.entities.ExampleSubChildEntity;
import ru.example.repositories.ExampleChildEntityRepository;
import ru.example.repositories.ExampleEntityRepository;
import ru.example.test.config.H2JpaConfiguration;
import ru.example.utils.JpaSpecificationsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void testExecution() {
        exampleEntityRepository.save(new ExampleEntity());
        SearchCriteria criterion = new SearchCriteria("id", SearchOperation.MORE, "0", null, JoinType.AND);

        assertEquals(1, exampleEntityRepository.findAll(specificationsBuilder.buildSpecification(criterion)).size());
    }

    @Test
    public void getWhereMoreAndLess() {
        exampleEntityRepository.save(new ExampleEntity(3, null));
        exampleEntityRepository.save(new ExampleEntity(5, null));
        exampleEntityRepository.save(new ExampleEntity(0, null));

        SearchCriteria criterion = new SearchCriteria(
                null, null, null,
                Arrays.asList(
                        new SearchCriteria("value", SearchOperation.MORE, "0", null, null),
                        new SearchCriteria("value", SearchOperation.LESS, "5", null, null)
                ),
                JoinType.AND
        );
        assertEquals(1, exampleEntityRepository.findAll(specificationsBuilder.buildSpecification(criterion)).size());
    }

    @Test
    public void testChildExecution() {
        ExampleChildEntity child1 = new ExampleChildEntity(1);
        ExampleChildEntity child2 = new ExampleChildEntity(2);
        ExampleChildEntity child3 = new ExampleChildEntity(3);

        exampleEntityRepository.save(new ExampleEntity(3, Collections.singletonList(child1)));
        exampleEntityRepository.save(new ExampleEntity(5, Collections.singletonList(child2)));
        exampleEntityRepository.save(new ExampleEntity(0, Collections.singletonList(child3)));

        SearchCriteria criterion = new SearchCriteria(
                null, null, null,
                Arrays.asList(
                        new SearchCriteria("children.value", SearchOperation.EQ, "1", null, null),
                        new SearchCriteria("children.value", SearchOperation.EQ, "3", null, null)
                ),
                JoinType.OR
        );
        List<ExampleEntity> entities = exampleEntityRepository.findAll(specificationsBuilder.buildSpecification(criterion));
        assertEquals(2, entities.size());
    }

    @Test
    public void testSubChildExecution() {

        ExampleSubChildEntity child1 = new ExampleSubChildEntity(1);
        ExampleSubChildEntity child2 = new ExampleSubChildEntity(2);
        ExampleSubChildEntity child3 = new ExampleSubChildEntity(3);

        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1, Collections.singletonList(child1)))));
        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1, Collections.singletonList(child2)))));
        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(3, Collections.singletonList(child3)))));

        SearchCriteria criterion = new SearchCriteria(
                null, null, null,
                Arrays.asList(
                        new SearchCriteria("children.children.value", SearchOperation.EQ, "1", null, null),
                        new SearchCriteria("children.children.value", SearchOperation.EQ, "3", null, null)
                ),
                JoinType.OR
        );
        List<ExampleEntity> entities = exampleEntityRepository.findAll(specificationsBuilder.buildSpecification(criterion));
        assertEquals(2, entities.size());
    }

    @Test
    public void testSubChildExecutionWithAnd() {

        ExampleSubChildEntity child1 = new ExampleSubChildEntity(1);
        ExampleSubChildEntity child2 = new ExampleSubChildEntity(2);
        ExampleSubChildEntity child3 = new ExampleSubChildEntity(3);

        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1, Collections.singletonList(child1)))));
        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1, Collections.singletonList(child2)))));
        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1, Collections.singletonList(child3)))));

        SearchCriteria criterion = new SearchCriteria(
                null, null, null,
                Arrays.asList(
                        new SearchCriteria("children.value", SearchOperation.EQ, "1", null, null),
                        new SearchCriteria(null, null, null,
                                Arrays.asList(
                                        new SearchCriteria("children.children.value", SearchOperation.EQ, "1", null, null),
                                        new SearchCriteria("children.children.value", SearchOperation.EQ, "3", null, null)
                                ),
                                JoinType.OR)
                ),
                JoinType.AND
        );
        List<ExampleEntity> entities = exampleEntityRepository.findAll(specificationsBuilder.buildSpecification(criterion));
        assertEquals(2, entities.size());
    }

    @Test
    public void multipleTablesJoin() {

        ExampleSecondChildEntity child2_1 = new ExampleSecondChildEntity(1);
        ExampleSecondChildEntity child2_2 = new ExampleSecondChildEntity(2);
        ExampleSecondChildEntity child2_3 = new ExampleSecondChildEntity(3);

        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1)), Collections.singletonList(child2_1)));
        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(1)), Collections.singletonList(child2_2)));
        exampleEntityRepository.save(new ExampleEntity(1, Collections.singletonList(new ExampleChildEntity(3)), Collections.singletonList(child2_3)));

        SearchCriteria criterion = new SearchCriteria(
                null, null, null,
                Arrays.asList(
                        new SearchCriteria("children.value", SearchOperation.EQ, "1", null, null),
                        new SearchCriteria(null, null, null,
                                Arrays.asList(
                                        new SearchCriteria("children2.value", SearchOperation.EQ, "1", null, null),
                                        new SearchCriteria("children2.value", SearchOperation.EQ, "3", null, null)
                                )
                                ,JoinType.OR
                        )
                ),
                JoinType.AND
        );
        List<ExampleEntity> entities = exampleEntityRepository.findAll(specificationsBuilder.buildSpecification(criterion));
        assertEquals(1, entities.size());
    }

}
