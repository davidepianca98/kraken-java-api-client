package com.github.sbouclier.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sbouclier.utils.StreamUtils;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * AssetPairsResult test
 *
 * @author Stéphane Bouclier
 */
public class AssetPairsResultTest {

    @Test
    public void should_return_to_string() throws IOException {

        // Given
        final String jsonResult = StreamUtils.getResourceAsString(this.getClass(), "json/asset_pairs.mock.json");
        AssetPairsResult mockResult = new ObjectMapper().readValue(jsonResult, AssetPairsResult.class);

        // When
        final String toString = mockResult.toString();

        // Then
        assertThat(toString, startsWith("AssetPairsResult"));
    }
}
