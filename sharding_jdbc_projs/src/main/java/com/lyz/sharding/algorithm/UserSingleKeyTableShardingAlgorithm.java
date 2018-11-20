package com.lyz.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * User分表逻辑，所以 %3
 *
 * @author iuyazhuang
 */
public class UserSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

    /**
     * sql 中 = 操作时，table的映射
     */
    @Override
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        for (String each : tableNames) {
            if (each.endsWith(("0".concat(String.valueOf(shardingValue.getValue() % 3))))) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * sql 中 in 操作时，table的映射
     */
    @Override
    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        for (Integer value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(("0".concat(String.valueOf(value % 3))))) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    /**
     * sql 中 between 操作时，table的映射
     */
    @Override
    public Collection<String> doBetweenSharding(Collection<String> tableNames,
                                                ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(("0".concat(String.valueOf(i % 3))))) {
                    result.add(each);
                }
            }
        }
        return result;
    }

}  
