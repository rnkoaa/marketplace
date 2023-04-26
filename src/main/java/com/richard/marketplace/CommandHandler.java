package com.richard.marketplace;

import com.richard.marketplace.types.Result;

public interface CommandHandler<V> {

    Result<V> handle(Object command);

}
