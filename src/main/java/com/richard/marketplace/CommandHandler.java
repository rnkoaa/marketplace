package com.richard.marketplace;

import com.richard.marketplace.types.Result;

public interface CommandHandler<V> {

    Result<V, Exception> handle(Object command);

}
