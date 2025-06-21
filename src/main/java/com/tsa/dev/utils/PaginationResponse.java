package com.tsa.dev.utils;

import java.util.List;

public record PaginationResponse<T>(
        List<T> content,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last

){}
