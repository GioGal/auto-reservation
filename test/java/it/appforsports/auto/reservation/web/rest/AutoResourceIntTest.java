package it.appforsports.auto.reservation.web.rest;

import it.appforsports.auto.reservation.AutoreservationApp;

import it.appforsports.auto.reservation.domain.Auto;
import it.appforsports.auto.reservation.repository.AutoRepository;
import it.appforsports.auto.reservation.service.AutoService;
import it.appforsports.auto.reservation.service.dto.AutoDTO;
import it.appforsports.auto.reservation.service.mapper.AutoMapper;
import it.appforsports.auto.reservation.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static it.appforsports.auto.reservation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AutoResource REST controller.
 *
 * @see AutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoreservationApp.class)
public class AutoResourceIntTest {

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELLO = "AAAAAAAAAA";
    private static final String UPDATED_MODELLO = "BBBBBBBBBB";

    private static final String DEFAULT_CILINDRATA = "AAAAAAAAAA";
    private static final String UPDATED_CILINDRATA = "BBBBBBBBBB";

    private static final String DEFAULT_ALIMENTAZIONE = "AAAAAAAAAA";
    private static final String UPDATED_ALIMENTAZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_COLORE = "AAAAAAAAAA";
    private static final String UPDATED_COLORE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNO_IMMATRICOLAZIONE = 1;
    private static final Integer UPDATED_ANNO_IMMATRICOLAZIONE = 2;

    private static final String DEFAULT_STATO_PRENOTAZIONE = "AAAAAAAAAA";
    private static final String UPDATED_STATO_PRENOTAZIONE = "BBBBBBBBBB";

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private AutoMapper autoMapper;

    @Autowired
    private AutoService autoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAutoMockMvc;

    private Auto auto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutoResource autoResource = new AutoResource(autoService);
        this.restAutoMockMvc = MockMvcBuilders.standaloneSetup(autoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auto createEntity(EntityManager em) {
        Auto auto = new Auto()
            .marca(DEFAULT_MARCA)
            .modello(DEFAULT_MODELLO)
            .cilindrata(DEFAULT_CILINDRATA)
            .alimentazione(DEFAULT_ALIMENTAZIONE)
            .colore(DEFAULT_COLORE)
            .annoImmatricolazione(DEFAULT_ANNO_IMMATRICOLAZIONE)
            .statoPrenotazione(DEFAULT_STATO_PRENOTAZIONE);
        return auto;
    }

    @Before
    public void initTest() {
        auto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuto() throws Exception {
        int databaseSizeBeforeCreate = autoRepository.findAll().size();

        // Create the Auto
        AutoDTO autoDTO = autoMapper.toDto(auto);
        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeCreate + 1);
        Auto testAuto = autoList.get(autoList.size() - 1);
        assertThat(testAuto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testAuto.getModello()).isEqualTo(DEFAULT_MODELLO);
        assertThat(testAuto.getCilindrata()).isEqualTo(DEFAULT_CILINDRATA);
        assertThat(testAuto.getAlimentazione()).isEqualTo(DEFAULT_ALIMENTAZIONE);
        assertThat(testAuto.getColore()).isEqualTo(DEFAULT_COLORE);
        assertThat(testAuto.getAnnoImmatricolazione()).isEqualTo(DEFAULT_ANNO_IMMATRICOLAZIONE);
        assertThat(testAuto.getStatoPrenotazione()).isEqualTo(DEFAULT_STATO_PRENOTAZIONE);
    }

    @Test
    @Transactional
    public void createAutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autoRepository.findAll().size();

        // Create the Auto with an existing ID
        auto.setId(1L);
        AutoDTO autoDTO = autoMapper.toDto(auto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setMarca(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelloIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setModello(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCilindrataIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setCilindrata(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlimentazioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setAlimentazione(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setColore(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnnoImmatricolazioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoRepository.findAll().size();
        // set the field null
        auto.setAnnoImmatricolazione(null);

        // Create the Auto, which fails.
        AutoDTO autoDTO = autoMapper.toDto(auto);

        restAutoMockMvc.perform(post("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isBadRequest());

        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutos() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);

        // Get all the autoList
        restAutoMockMvc.perform(get("/api/autos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auto.getId().intValue())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
            .andExpect(jsonPath("$.[*].modello").value(hasItem(DEFAULT_MODELLO.toString())))
            .andExpect(jsonPath("$.[*].cilindrata").value(hasItem(DEFAULT_CILINDRATA.toString())))
            .andExpect(jsonPath("$.[*].alimentazione").value(hasItem(DEFAULT_ALIMENTAZIONE.toString())))
            .andExpect(jsonPath("$.[*].colore").value(hasItem(DEFAULT_COLORE.toString())))
            .andExpect(jsonPath("$.[*].annoImmatricolazione").value(hasItem(DEFAULT_ANNO_IMMATRICOLAZIONE)))
            .andExpect(jsonPath("$.[*].statoPrenotazione").value(hasItem(DEFAULT_STATO_PRENOTAZIONE.toString())));
    }

    @Test
    @Transactional
    public void getAuto() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);

        // Get the auto
        restAutoMockMvc.perform(get("/api/autos/{id}", auto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auto.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.modello").value(DEFAULT_MODELLO.toString()))
            .andExpect(jsonPath("$.cilindrata").value(DEFAULT_CILINDRATA.toString()))
            .andExpect(jsonPath("$.alimentazione").value(DEFAULT_ALIMENTAZIONE.toString()))
            .andExpect(jsonPath("$.colore").value(DEFAULT_COLORE.toString()))
            .andExpect(jsonPath("$.annoImmatricolazione").value(DEFAULT_ANNO_IMMATRICOLAZIONE))
            .andExpect(jsonPath("$.statoPrenotazione").value(DEFAULT_STATO_PRENOTAZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuto() throws Exception {
        // Get the auto
        restAutoMockMvc.perform(get("/api/autos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuto() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);
        int databaseSizeBeforeUpdate = autoRepository.findAll().size();

        // Update the auto
        Auto updatedAuto = autoRepository.findOne(auto.getId());
        updatedAuto
            .marca(UPDATED_MARCA)
            .modello(UPDATED_MODELLO)
            .cilindrata(UPDATED_CILINDRATA)
            .alimentazione(UPDATED_ALIMENTAZIONE)
            .colore(UPDATED_COLORE)
            .annoImmatricolazione(UPDATED_ANNO_IMMATRICOLAZIONE)
            .statoPrenotazione(UPDATED_STATO_PRENOTAZIONE);
        AutoDTO autoDTO = autoMapper.toDto(updatedAuto);

        restAutoMockMvc.perform(put("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isOk());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeUpdate);
        Auto testAuto = autoList.get(autoList.size() - 1);
        assertThat(testAuto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testAuto.getModello()).isEqualTo(UPDATED_MODELLO);
        assertThat(testAuto.getCilindrata()).isEqualTo(UPDATED_CILINDRATA);
        assertThat(testAuto.getAlimentazione()).isEqualTo(UPDATED_ALIMENTAZIONE);
        assertThat(testAuto.getColore()).isEqualTo(UPDATED_COLORE);
        assertThat(testAuto.getAnnoImmatricolazione()).isEqualTo(UPDATED_ANNO_IMMATRICOLAZIONE);
        assertThat(testAuto.getStatoPrenotazione()).isEqualTo(UPDATED_STATO_PRENOTAZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuto() throws Exception {
        int databaseSizeBeforeUpdate = autoRepository.findAll().size();

        // Create the Auto
        AutoDTO autoDTO = autoMapper.toDto(auto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAutoMockMvc.perform(put("/api/autos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autoDTO)))
            .andExpect(status().isCreated());

        // Validate the Auto in the database
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuto() throws Exception {
        // Initialize the database
        autoRepository.saveAndFlush(auto);
        int databaseSizeBeforeDelete = autoRepository.findAll().size();

        // Get the auto
        restAutoMockMvc.perform(delete("/api/autos/{id}", auto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Auto> autoList = autoRepository.findAll();
        assertThat(autoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auto.class);
        Auto auto1 = new Auto();
        auto1.setId(1L);
        Auto auto2 = new Auto();
        auto2.setId(auto1.getId());
        assertThat(auto1).isEqualTo(auto2);
        auto2.setId(2L);
        assertThat(auto1).isNotEqualTo(auto2);
        auto1.setId(null);
        assertThat(auto1).isNotEqualTo(auto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoDTO.class);
        AutoDTO autoDTO1 = new AutoDTO();
        autoDTO1.setId(1L);
        AutoDTO autoDTO2 = new AutoDTO();
        assertThat(autoDTO1).isNotEqualTo(autoDTO2);
        autoDTO2.setId(autoDTO1.getId());
        assertThat(autoDTO1).isEqualTo(autoDTO2);
        autoDTO2.setId(2L);
        assertThat(autoDTO1).isNotEqualTo(autoDTO2);
        autoDTO1.setId(null);
        assertThat(autoDTO1).isNotEqualTo(autoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autoMapper.fromId(null)).isNull();
    }
}
