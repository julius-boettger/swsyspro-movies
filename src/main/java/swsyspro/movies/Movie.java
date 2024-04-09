package swsyspro.movies;

import lombok.NonNull;

public record Movie(long id, @NonNull String name) {}
