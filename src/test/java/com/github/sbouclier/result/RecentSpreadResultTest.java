package com.github.sbouclier.result;

import com.github.sbouclier.MockInitHelper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * RecentSpreadResult test
 *
 * @author Stéphane Bouclier
 */
public class RecentSpreadResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        RecentSpreadResult mockResult = MockInitHelper.buildRecentSpreadResult();

        // When
        final String toString = mockResult.toString();

        // Then
        assertThat(toString, startsWith("RecentSpreadResult"));
    }
}