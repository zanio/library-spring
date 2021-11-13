package com.book.library.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class FavoriteDao {
    @NotNull
    private Set<@NotNull Integer> books;
}
