package nextstep.subway.favorite.ui;

import java.net.URI;
import java.util.List;
import nextstep.subway.auth.domain.AuthenticationPrincipal;
import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.favorite.domain.Favorite;
import nextstep.subway.favorite.dto.FavoriteRequest;
import nextstep.subway.favorite.dto.FavoriteResponse;
import nextstep.subway.favorite.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> findFavoritesByMemberId(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok().body(favoriteService.findFavoritesByMemberId(loginMember));
    }

    @PostMapping
    public ResponseEntity<Void> createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @RequestBody FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteService.createFavorite(loginMember, favoriteRequest);
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(loginMember, favoriteId);
        return ResponseEntity.noContent().build();
    }
}
