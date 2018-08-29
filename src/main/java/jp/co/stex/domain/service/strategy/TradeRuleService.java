package jp.co.stex.domain.service.strategy;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.stex.domain.mapper.strategy.TradeRuleMapper;
import jp.co.stex.domain.mapper.strategy.TradeStrategyMapper;
import jp.co.stex.domain.model.strategy.TradeRuleEntity;
import jp.co.stex.domain.model.strategy.TradeRules;
import jp.co.stex.domain.model.strategy.TradeStrategyEntity;
import jp.co.stex.domain.model.strategy.code.TradeType;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * <p>取引戦略を操作するサービスです。</p>
 *
 * @author t.nemoto.x
 */
@Service
@RequiredArgsConstructor
public class TradeRuleService implements ITradeRuleService {

    private final TradeRuleMapper tradeRuleMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeRuleEntity> findAllTradeRule(int uid, int sid, TradeType tradeType) {
        return tradeRuleMapper.findAllByTradeType(uid, sid, tradeType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAllTradeRule(TradeRules tradeRules) {
        List<TradeRuleEntity> willCreateRules = tradeRules.makeWillCreatedRules();
        List<TradeRuleEntity> willUpdateRules = tradeRules.makeWillUpdatedRules();
        List<Integer> existRids = tradeRules.extractRids();
//        tradeRuleMapper.updateAll(rules);
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String json = "{\"aaa\": \"aaa\", \"name\": \"takuya nemoto\", \"children\": [" +
            "{\"age\": 1, \"name\": \"yataro nemoto\"}," +
            "{\"name\": \"akiko nemoto\"}" +
            "]}";
        Person person = mapper.readValue(json, Person.class);
        System.out.println(person);
        System.out.println(mapper.writeValueAsString(person));
    }

    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Person {

        @JsonCreator
        public Person(@JsonProperty("age") Age age, @JsonProperty("name") Name name, @JsonProperty("children") Children children) {
            this.age = age;
            this.name = name;
            this.children = children;
        }

        private Age age;
        private Name name;
        private Children children;
    }

    @ToString
    public static class Age {

        @JsonCreator
        Age(int age) {
            this.age = age;
        }

        @JsonValue
        int age;
    }

    @ToString
    public static class Name {

        @JsonCreator
        Name(String name) {
            String[] args = name.split(" ");
            firstName = args[0];
            lastName = args.length > 1 ? args[1] : "";
        }

        private String firstName;
        private String lastName;

        @JsonValue
        public String fullName() {
            return firstName + " " + lastName;
        }
    }

    @ToString
    static class Children {
        @JsonCreator
        Children(List<Person> children) {
            this.people = children == null ? Collections.emptyList() : children;
        }

        @JsonValue
        private List<Person> people;
    }
}
