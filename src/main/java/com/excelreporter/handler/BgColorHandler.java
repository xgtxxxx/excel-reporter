package com.excelreporter.handler;

import com.excelreporter.annotation.BgColor;
import com.excelreporter.annotation.BgColors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BgColorHandler {

    private final List<RowMatcher> rowMatchers;

    public BgColorHandler(final BgColors bgColors) {
        final BgColor[] colors = bgColors.colors();
        rowMatchers =
            Arrays.asList(colors)
            .stream()
            .map(bgColor -> new RowMatcher(bgColor))
            .collect(Collectors.toList());

        Collections.reverse(rowMatchers);
    }

    public Optional<Short> getColor(final int rowIndex) {
        final Optional<RowMatcher> matcher = rowMatchers
            .stream()
            .filter(rowMatcher -> rowMatcher.isMatched(rowIndex))
            .findFirst();

        if(matcher.isPresent()) {
            return Optional.of(matcher.get().getColor());
        }

        return Optional.empty();
    }

    private class RowMatcher {
        private final short color;
        private final int index;
        private final int stepLength;
        private final int loopSkip;

        RowMatcher(final BgColor bgColor) {
            assert bgColor.stepLength() > 0;
            assert bgColor.loopSkipLength() >= 0;

            this.index = bgColor.rowIndex();
            this.loopSkip = bgColor.loopSkipLength();
            this.stepLength = bgColor.stepLength();
            this.color = bgColor.color();
        }

        public boolean isMatched(final int rowIndex) {
            //All row is matched
            if(index == -1) {
                return true;
            }

            if(rowIndex < index) {
                return false;
            }

            //Only for the rows declared from index to (index + stepLength)
            if(loopSkip == 0) {
                return rowIndex - index < stepLength;
            }

            //Loop
            if(loopSkip > 0) {
                final int loopLength = stepLength + loopSkip;
                return (rowIndex - index) % loopLength < stepLength;
            }

            return false;
        }

        public short getColor() {
            return color;
        }
    }
}
