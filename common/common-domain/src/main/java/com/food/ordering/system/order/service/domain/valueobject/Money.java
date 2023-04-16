package com.food.ordering.system.order.service.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class Money {

    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        if(this.amount != null){
            return this.amount.compareTo(BigDecimal.ZERO) > 0;
        }else {
            return false;
        }
    }

    public boolean isGreaterThan(Money money){
        if(this.amount != null && money != null){
            return this.amount.compareTo(money.getAmount()) > 0;
        }else {
            return false;
        }
    }

    public Money add(Money money){
        return new Money(setScale(this.amount.add(money.getAmount())));
    }

    public Money subtract(Money money){
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }

    public Money multiply(int multiplier){
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(BigDecimal amount){
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
