package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TopStoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopStory.class);
        TopStory topStory1 = new TopStory();
        topStory1.setId("id1");
        TopStory topStory2 = new TopStory();
        topStory2.setId(topStory1.getId());
        assertThat(topStory1).isEqualTo(topStory2);
        topStory2.setId("id2");
        assertThat(topStory1).isNotEqualTo(topStory2);
        topStory1.setId(null);
        assertThat(topStory1).isNotEqualTo(topStory2);
    }
}
