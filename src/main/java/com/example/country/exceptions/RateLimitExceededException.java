package com.example.country.exceptions;

public class RateLimitExceededException extends RuntimeException
{
    public RateLimitExceededException(final String message)
    {
        super(message);
    }
}
