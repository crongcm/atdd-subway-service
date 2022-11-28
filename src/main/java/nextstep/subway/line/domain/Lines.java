package nextstep.subway.line.domain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.station.domain.Station;

public class Lines {
    private List<Line> lines;

    private Lines() {}

    private Lines(List<Line> lines) {
        this.lines = lines;
    }

    public static Lines from(List<Line> lines) {
        return new Lines(lines);
    }

    public List<Station> stations() {
        return lines.stream()
                .map(Line::sortStations)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Section> sections() {
        return lines.stream()
                .map(Line::sections)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
