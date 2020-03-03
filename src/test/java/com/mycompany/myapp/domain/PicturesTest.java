package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PicturesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pictures.class);
        Pictures pictures1 = new Pictures();
        pictures1.setId("id1");
        Pictures pictures2 = new Pictures();
        pictures2.setId(pictures1.getId());
        assertThat(pictures1).isEqualTo(pictures2);
        pictures2.setId("id2");
        assertThat(pictures1).isNotEqualTo(pictures2);
        pictures1.setId(null);
        assertThat(pictures1).isNotEqualTo(pictures2);
    }
}
