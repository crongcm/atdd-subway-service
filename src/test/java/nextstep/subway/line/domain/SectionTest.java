package nextstep.subway.line.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SectionTest {
    private Line line;

    @BeforeEach
    void setUp() {
        line = Line.of("신분당선", "bg-red-600",
                Section.of(Station.from("논현역"), Station.from("강남역"), Distance.from(10)));
    }

    @Test
    @DisplayName("구간 생성")
    void createSection() {
        // given
        Station upStation = Station.from("논현역");
        Station downStation = Station.from("강남역");
        Distance distance = Distance.from(10);

        // when
        Section actual = Section.of(upStation, downStation, distance);

        // then
        assertAll(
                () -> assertThat(actual.getUpStation()).isEqualTo(upStation),
                () -> assertThat(actual.getDownStation()).isEqualTo(downStation),
                () -> assertThat(actual.getDistance().getDistance()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("구간 생성 필수값 검증 (이름, 거리)")
    void createSectionException() {
        // given
        Station upStation = Station.from("강남역");
        Station downStation = Station.from("양재역");
        Distance distance = Distance.from(10);

        // when & then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Section.of(null, downStation, distance)),
                () -> assertThrows(IllegalArgumentException.class, () -> Section.of(upStation, null, distance)),
                () -> assertThrows(IllegalArgumentException.class, () -> Section.of(upStation, downStation, null))
        );
    }

    @Test
    @DisplayName("기존역 사이 길이보다 크거나 같으면 구간 생성 불가능")
    void distance() {
        // given
        Station upStation = Station.from("논현역");
        Station downStation = Station.from("신논현역");
        Distance distance = Distance.from(10);

        // when & then
        assertThatThrownBy(() -> line.addSection(Section.of(upStation, downStation, distance)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기존 노선의 거리보다 작거나 같을 수 없습니다.");
    }


    @Test
    @DisplayName("기존역과 상행/하행역이 모두 같으면 구간 생성 불가능")
    void sameSection() {
        // given
        Station upStation = Station.from("논현역");
        Station downStation = Station.from("강남역");
        Distance distance = Distance.from(5);

        // when & then
        assertThatThrownBy(() -> line.addSection(Section.of(upStation, downStation, distance)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 등록된 구간 입니다.");
    }

    @Test
    @DisplayName("기존역과 일치하는 상행역 또는 하행역이 없을경우 구간 생성 불가능")
    void notContainSection() {
        // given
        Station upStation = Station.from("신논현역");
        Station downStation = Station.from("판교역");
        Distance distance = Distance.from(5);

        // when & then
        assertThatThrownBy(() -> line.addSection(Section.of(upStation, downStation, distance)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록할 수 없는 구간 입니다.");
    }

    @Test
    @DisplayName("역과 역 사이에 구간 추가")
    void addSectionBetweenStations() {
        // given
        Station upStation = Station.from("논현역");
        Station downStation = Station.from("신논현역");
        Distance distance = Distance.from(5);

        // when
        line.addSection(Section.of(upStation, downStation, distance));

        // then
        assertAll(
                () -> assertThat(line.sortStations()).hasSize(3),
                () -> assertThat(line.totalDistance()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("구간 추가 상행종점 추가")
    void addSectionUpStation() {
        // given
        Station upStation = Station.from("신사역");
        Station downStation = Station.from("논현역");
        Distance distance = Distance.from(5);

        // when
        line.addSection(Section.of(upStation, downStation, distance));

        // then
        assertAll(
                () -> assertThat(line.sortStations()).hasSize(3),
                () -> assertThat(line.totalDistance()).isEqualTo(15)
        );
    }

    @Test
    @DisplayName("구간 추가 하행종점 추가")
    void addSectionDownStation() {
        // given
        Station upStation = Station.from("강남역");
        Station downStation = Station.from("판교역");
        Distance distance = Distance.from(10);

        // when
        line.addSection(Section.of(upStation, downStation, distance));

        // then
        assertAll(
                () -> assertThat(line.sortStations()).hasSize(3),
                () -> assertThat(line.totalDistance()).isEqualTo(20)
        );
    }

    @Test
    @DisplayName("구간 내 상행 종점 역 제거")
    void removeSectionByUpStation() {
        // given
        Station upStation = Station.from("신사역");
        Station downStation = Station.from("논현역");
        Distance distance = Distance.from(5);
        line.addSection(Section.of(upStation, downStation, distance));

        // when
        line.removeSection(upStation);

        // then
        assertAll(
            () -> assertThat(line.sortStations()).hasSize(2),
            () -> assertThat(line.sortStations()).doesNotContain(upStation),
            () -> assertThat(line.totalDistance()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("구간 내 하행 종점 역 제거")
    void removeSectionByDownStation() {
        // given
        Station upStation = Station.from("강남역");
        Station downStation = Station.from("판교역");
        Distance distance = Distance.from(10);
        line.addSection(Section.of(upStation, downStation, distance));

        // when
        line.removeSection(downStation);

        // then
        assertAll(
                () -> assertThat(line.sortStations()).hasSize(2),
                () -> assertThat(line.sortStations()).doesNotContain(downStation),
                () -> assertThat(line.totalDistance()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("구간 내 중간 역 제거")
    void removeSectionByIntermediateStation() {
        // given
        Station upStation = Station.from("강남역");
        Station downStation = Station.from("판교역");
        Distance distance = Distance.from(10);
        line.addSection(Section.of(upStation, downStation, distance));

        // when
        line.removeSection(upStation);

        // then
        assertAll(
                () -> assertThat(line.sortStations()).hasSize(2),
                () -> assertThat(line.sortStations()).doesNotContain(upStation),
                () -> assertThat(line.totalDistance()).isEqualTo(20)
        );
    }

    @Test
    @DisplayName("노선 내 구간이 1개일 경우 역 제거시 에러 반환")
    void deleteStationDefaultSectionSize() {
        // when & then
        assertThatThrownBy(() -> line.removeSection(Station.from("논현역")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("노선의 구간은 1개 이상 존재해야 합니다.");
    }

    @Test
    @DisplayName("노선 내 존재하지 않는 역 제거시 에러 반환")
    void deleteStationNotContainLineThrowException() {
        // given
        Station upStation = Station.from("강남역");
        Station downStation = Station.from("판교역");
        Distance distance = Distance.from(10);
        line.addSection(Section.of(upStation, downStation, distance));

        // when & then
        assertThatThrownBy(() -> line.removeSection(Station.from("신사역")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 역이 존재하지 않습니다.");
    }
}
