package ru.wildberries.expectedconditions;

import ru.wildberries.asserts.AssertHelper;

public abstract class ExpectedConditions<K> implements IExpectedCondition<K, Boolean> {

    @Override
    public boolean isTrue(K k) {
        boolean isTrue = apply(k);
        AssertHelper.assertTrue(toString(), isTrue);
        return isTrue;
    }
}
