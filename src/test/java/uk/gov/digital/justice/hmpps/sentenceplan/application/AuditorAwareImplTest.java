package uk.gov.digital.justice.hmpps.sentenceplan.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AuditorAwareImplTest {

    @Mock
    RequestData requestData;

    private AuditorAwareImpl auditorAware;

    @Before
    public void setup() {
        auditorAware = new AuditorAwareImpl(requestData);
    }


    @Test
    public void shouldReturnMDCUsername() {
        when(requestData.getUsername()).thenReturn("user Id");

        assertThat(auditorAware.getCurrentAuditor().get()).isEqualTo("user Id");

    }

}