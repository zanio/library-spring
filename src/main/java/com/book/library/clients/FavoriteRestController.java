package com.book.library.clients;

import com.book.library.assemblers.FavoriteModelAssemblers;
import com.book.library.dao.FavoriteDao;
import com.book.library.entities.Favorite;
import com.book.library.services.favorite.FavoriteService;
import com.book.library.util.HelperClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/favorite")
public class FavoriteRestController {
    private final FavoriteModelAssemblers favoriteModelAssemblers;
    private final PagedResourcesAssembler<Favorite> favoritePagedResourcesAssembler;
    private final FavoriteService favoriteService;

    public FavoriteRestController(FavoriteModelAssemblers favoriteModelAssemblers,
                                  PagedResourcesAssembler<Favorite> favoritePagedResourcesAssembler,
                                  FavoriteService favoriteService) {
        this.favoriteModelAssemblers = favoriteModelAssemblers;
        this.favoritePagedResourcesAssembler = favoritePagedResourcesAssembler;
        this.favoriteService = favoriteService;
    }


    @GetMapping(value = "{favoriteId}", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFavoriteById(
            @PathVariable("favoriteId") @NotNull final String bookId
    ) {
        return HelperClass.generateResponse(
                "Favorite successfully retrieved " + "",
                HttpStatus.OK,
                favoriteModelAssemblers.toModel(
                        favoriteService.findOne(Integer.parseInt(bookId))
                )
        );
    }

    @PostMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createFavorite(
            @RequestBody @NotNull @Valid final FavoriteDao favoriteDao
    ) {
        return HelperClass.generateResponse(
                "Favorite books successfully created " + "",
                HttpStatus.CREATED,
                favoriteModelAssemblers.toModel(
                        favoriteService.create(favoriteDao)
                )
        );
    }


    @GetMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFavorites(
            @PageableDefault(
                    sort = { "createdAt" },
                    direction = Sort.Direction.DESC
            ) final Pageable pageable
            ) {
        return HelperClass.generateResponse(
                "Favorites successfully retrieved " + "",
                HttpStatus.OK,
                favoritePagedResourcesAssembler.toModel(
                        favoriteService.listFavorite(pageable),
                        favoriteModelAssemblers
                )
        );
    }

}
