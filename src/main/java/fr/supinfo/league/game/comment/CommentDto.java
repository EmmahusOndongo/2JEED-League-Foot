package fr.supinfo.league.game.comment;

import java.util.UUID;

public record CommentDto(UUID id, String content, String author) {
}
