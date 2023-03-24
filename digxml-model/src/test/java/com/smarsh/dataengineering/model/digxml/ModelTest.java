package com.smarsh.dataengineering.model.digxml;

import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private static final File xsdFile = new File("src/main/resources/xsd/actiance.sfab/cis/dig/itm-transcript-1.0.xsd");
    private static String dataDir = "src/test/resources/data/";
    private static final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static Schema schema;

    @BeforeAll
    static void init() throws Exception {
        schema = sf.newSchema(xsdFile);
    }

    @ParameterizedTest(name = "validate[{index}] {0} is valid: {1}")
    @MethodSource("validFileLister")
    void weCanValidateWithSchema(final String filename, final boolean isValid) {
        File xmlFile = new File("%s/%s".formatted(dataDir, filename));

        Validator validator;

        validator = schema.newValidator();

        assertNotNull(validator);

        if (isValid) {
            assertDoesNotThrow(() -> validator.validate(new StreamSource(xmlFile)));
        } else {
            assertThrows(SAXException.class, () -> validator.validate(new StreamSource(xmlFile)));
        }
    }

    @Test
    void validateParsingOfSample() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Transcript.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        var t = (Transcript) unmarshaller.unmarshal(new FileReader(new File("src/test/resources/data/generated-transcript.xml")));

        assertEquals("string", t.getId());
        assertEquals("string", t.getSourceEndpointId());
        assertEquals("string", t.getSourceEndpointVersion());
        assertEquals(1222652985000L, DateTimeUtils.toEpochMillis(t.getTimeStamp()));

        Interaction interaction = t.getInteraction();
        assertNotNull(interaction);

        // validate interaction attributes
        assertEquals("string", interaction.getInteractionEventId());
        assertFalse(interaction.isHistoricalDataFlag());
        assertEquals("valid", interaction.getDataState());
        assertEquals("sha1", interaction.getCocHashAlgorithm());
        assertEquals("hex", interaction.getCocHashEncoding());
        assertEquals("full", interaction.getCocHashType());
        assertEquals("string", interaction.getAttributeCocMasterHashValue());
        assertEquals("string", interaction.getCocHashValue());
        assertEquals("string", interaction.getCocVersion());

        // validate interaction timeframe
        TimeFrame interactionTimeFrame = interaction.getTimeFrame();
        assertNotNull(interactionTimeFrame);
        assertEquals("anySimpleType", interactionTimeFrame.getStartTime().getDescription());
        assertEquals(1411082313000L, DateTimeUtils.toEpochMillis(interactionTimeFrame.getStartTime().getTimestamp()));
        assertEquals("anySimpleType", interactionTimeFrame.getEndTime().getDescription());
        assertEquals(1156008434000L, DateTimeUtils.toEpochMillis(interactionTimeFrame.getEndTime().getTimestamp()));

        assertEquals("string", interaction.getCommunicationObjectId());
        assertEquals("string", interaction.getParentCommunicationObjectId());
        assertEquals("string", interaction.getResourceLocation());

        // validate interaction modality
        Modality interactionModality = interaction.getModality();
        assertNotNull(interactionModality);
        assertEquals(ModalityClass.SOCIAL, interactionModality.getClassification());
        assertEquals(ModalityType.PRIVATE, interactionModality.getType());
        assertEquals("string", interactionModality.getVendor());
        assertEquals("string", interactionModality.getDescription());
        assertEquals("string", interactionModality.getChannel());
        assertEquals("string", interactionModality.getNetwork());

        // validate interaction subject
        Subject interactionSubject = interaction.getSubject();
        assertNotNull(interactionSubject);
        assertEquals("string", interactionSubject.getValue());
        assertEquals(MimeType.TEXT_PLAIN, interactionSubject.getContentType());

        // validate interaction attributes
        IndexableAttributes interactionAttributes = interaction.getAttributes();
        assertNotNull(interactionAttributes);
        assertNotNull(interactionAttributes.getAttribute());
        assertEquals(1, interactionAttributes.getAttribute().size());
        var interactionAttribute = interactionAttributes.getAttribute().get(0);
        assertEquals("string", interactionAttribute.getName());
        assertEquals(MimeType.TEXT_PLAIN, interactionAttribute.getContentType());
        assertTrue(interactionAttribute.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, interactionAttribute.getUserVisible());
        assertEquals("", interactionAttribute.getClassification());
        assertFalse(interactionAttribute.isSystemFlag());

        // validate participants
        var participants = interaction.getParticipants();
        assertNotNull(participants);
        assertNotNull(participants.getParticipant());
        assertEquals(1, participants.getParticipant().size());
        var participant = participants.getParticipant().get(0);
        assertEquals("string", participant.getParticipantId());
        assertEquals("participant", participant.getParticipantRole());

        // validate participant network info
        var networkInfo = participant.getNetworkInfo();
        assertNotNull(networkInfo);
        assertEquals("login", networkInfo.getEndpointIdType());
        assertEquals("string", networkInfo.getEndpointId());
        assertEquals("string", networkInfo.getDisplayName());
        assertNotNull(networkInfo.getGroups());
        assertNotNull(networkInfo.getGroups().getGroup());
        assertEquals(1, networkInfo.getGroups().getGroup().size());
        assertEquals("string", networkInfo.getGroups().getGroup().get(0));

        // validate participant user info
        var userInfo = participant.getUserInfo();
        assertNotNull(userInfo);
        assertEquals("string", userInfo.getUserId());
        assertEquals("internal", userInfo.getUserType());
        assertEquals("string", userInfo.getEmailAddress());
        var userInfoName = userInfo.getName();
        assertNotNull(userInfoName);
        assertEquals("string", userInfoName.getFirst());
        assertEquals("string", userInfoName.getMiddle());
        assertEquals("string", userInfoName.getLast());
        assertEquals("string", userInfoName.getInitials());
        assertEquals("string", userInfoName.getDisplayName());
        var userInfoAffiliation = userInfo.getAffiliation();
        assertNotNull(userInfoAffiliation);
        assertEquals("string", userInfoAffiliation.getEmployeeId());
        assertEquals("string", userInfoAffiliation.getTitle());
        assertEquals("string", userInfoAffiliation.getDepartment());
        assertEquals("string", userInfoAffiliation.getDivision());
        assertEquals("string", userInfoAffiliation.getCompany());
        assertEquals("string", userInfoAffiliation.getBuilding());
        var userInfoGeoLocation = userInfo.getGeoLocation();
        assertNotNull(userInfoGeoLocation);
        assertEquals("string", userInfoGeoLocation.getCity());
        assertEquals("string", userInfoGeoLocation.getState());
        assertEquals("string", userInfoGeoLocation.getCountry());
        var userInfoPhoneNumbers = userInfo.getPhoneNumbers();
        assertNotNull(userInfoPhoneNumbers);
        assertEquals("string", userInfoPhoneNumbers.getOfficePhone());
        assertEquals("string", userInfoPhoneNumbers.getSecondaryOfficePhone());
        assertEquals("string", userInfoPhoneNumbers.getMobilePhone());
        assertEquals("string", userInfoPhoneNumbers.getHomePhone());
        assertEquals("string", userInfoPhoneNumbers.getOtherPhone());
        var userInfoSecondaryAddresses = userInfo.getSecondaryAddresses();
        assertNotNull(userInfoSecondaryAddresses);
        assertNotNull(userInfoSecondaryAddresses.getSecondaryAddress());
        assertEquals(1, userInfoSecondaryAddresses.getSecondaryAddress().size());
        assertEquals("string", userInfoSecondaryAddresses.getSecondaryAddress().get(0).getValue());
        assertEquals("sip", userInfoSecondaryAddresses.getSecondaryAddress().get(0).getAddressType());
        var userInfoGroups = userInfo.getGroups();
        assertNotNull(userInfoGroups);
        assertNotNull(userInfoGroups.getGroup());
        assertEquals(1, userInfoGroups.getGroup().size());
        assertEquals("string", userInfoGroups.getGroup().get(0));
        var userInfoImage = userInfo.getImage();
        assertNotNull(userInfoImage);
        assertEquals("jpeg", userInfoImage.getType());
        assertEquals("ZnJlbXVudA==", Base64.getEncoder().encodeToString(userInfoImage.getValue()));
        var userInfoModificationTime = userInfo.getModificationTime();
        assertNotNull(userInfoModificationTime);
        assertEquals("anySimpleType", userInfoModificationTime.getDescription());
        assertEquals(1347541234000L, DateTimeUtils.toEpochMillis(userInfoModificationTime.getTimestamp()));

        // validate file events
        var fileEvents = interaction.getFileEvents();
        assertNotNull(fileEvents);
        assertNotNull(fileEvents.getFileEvent());
        assertFalse(fileEvents.getFileEvent().isEmpty());
        var fileEvent = fileEvents.getFileEvent().get(0);
        assertEquals("string", fileEvent.getFileEventId());
        assertEquals(FileEventType.BODY, fileEvent.getFileEventType());
        assertEquals(Action.CREATE, fileEvent.getAction());
        assertFalse(fileEvent.isHistoryFlag());
        assertEquals("string", fileEvent.getCocHashValue());
        var fileObject = fileEvent.getFileObject();
        assertNotNull(fileObject);
        assertEquals("string", fileObject.getFileObjectId());
        assertEquals("anySimpleType", fileObject.getEventTime().getDescription());
        assertEquals(1455980189000L, DateTimeUtils.toEpochMillis(fileObject.getEventTime().getTimestamp()));
        assertEquals("string", fileObject.getParticipantId());
        assertEquals("http://www.company.edu/profundum/bella", fileObject.getFileObjectUrl());
        var fileContent = fileObject.getFileContent();
        assertNotNull(fileContent);
        assertEquals("http://www.any.edu/ac/hoc", fileContent.getFilePath());
        assertEquals("string", fileContent.getName());
        assertEquals("text/plain", fileContent.getContentType());
        assertEquals(Encoding.BASE_64, fileContent.getEncoding());
        assertTrue(fileContent.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, fileContent.getUserVisible());
        assertEquals("cmFwaWR1bQ==", Base64.getEncoder().encodeToString(fileContent.getValue()));
        var fileObjectAttributes = fileObject.getAttributes();
        assertNotNull(fileObjectAttributes);
        assertNotNull(fileObjectAttributes.getAttribute());
        assertEquals(1, fileObjectAttributes.getAttribute().size());
        var fileObjectAttribute = fileObjectAttributes.getAttribute().get(0);
        assertEquals("string", fileObjectAttribute.getName());
        assertEquals(MimeType.TEXT_PLAIN, fileObjectAttribute.getContentType());
        assertTrue(fileObjectAttribute.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, fileObjectAttribute.getUserVisible());
        assertEquals("", fileObjectAttribute.getClassification());
        assertFalse(fileObjectAttribute.isSystemFlag());
        assertEquals("string", fileObjectAttribute.getValue().trim());
        var fileObjectLexiconHits = fileObject.getLexiconHits();
        assertNotNull(fileObjectLexiconHits);
        assertNotNull(fileObjectLexiconHits.getLexiconHit());
        assertEquals(1, fileObjectLexiconHits.getLexiconHit().size());
        var fileObjectLexiconHit = fileObjectLexiconHits.getLexiconHit().get(0);
        assertEquals("near characters", fileObjectLexiconHit.getMatchType());
        assertEquals(BigInteger.valueOf(100L), fileObjectLexiconHit.getNearDistance());
        assertFalse(fileObjectLexiconHit.isCaseSensitive());
        assertTrue(fileObjectLexiconHit.isPlainText());
        assertEquals(10L, fileObjectLexiconHit.getOffset());
        assertEquals(BigInteger.valueOf(100L), fileObjectLexiconHit.getLength());
        assertEquals("string", fileObjectLexiconHit.getOrigin());
        assertEquals("string", fileObjectLexiconHit.getCategory());
        assertEquals("string", fileObjectLexiconHit.getPolicy());
        assertEquals("string", fileObjectLexiconHit.getValue().trim());

        // validate text events
        var textEvents = interaction.getTextEvents();
        assertNotNull(textEvents);
        assertNotNull(textEvents.getTextEvent());
        assertEquals(1, textEvents.getTextEvent().size());
        var textEvent = textEvents.getTextEvent().get(0);
        assertEquals("string", textEvent.getTextEventId());
        assertEquals(Action.CREATE, textEvent.getAction());
        assertEquals("string", textEvent.getOperation());
        assertFalse(textEvent.isHistoryFlag());
        assertEquals("string", textEvent.getCocHashValue());
        var textObject = textEvent.getTextObject();
        assertNotNull(textObject);
        assertEquals("string", textObject.getTextObjectId());
        assertEquals("string", textObject.getParentTextObjectId());
        assertFalse(textObject.isSystemFlag());
        assertEquals(TextEventType.COMMENT, textObject.getTextObjectStyle());
        assertEquals("string", textObject.getTextObjectType());
        assertEquals("string", textObject.getTextObjectSubType());
        assertEquals("string", textObject.getParticipantId());
        assertEquals("http://www.any.com/claustra/circum", textObject.getTextObjectUrl());
        assertEquals("string", textObject.getLinkCommunicationObjectId());
        var textObjectEventTime = textObject.getEventTime();
        assertNotNull(textObjectEventTime);
        assertEquals("anySimpleType", textObjectEventTime.getDescription());
        assertEquals(1440371363000L, DateTimeUtils.toEpochMillis(textObjectEventTime.getTimestamp()));
        var fileEventIds = textObject.getFileEventIds();
        assertNotNull(fileEventIds);
        assertNotNull(fileEventIds.getFileEventId());
        assertEquals(1, fileEventIds.getFileEventId().size());
        var fileEventId = fileEventIds.getFileEventId().get(0);
        assertEquals("string", fileEventId);
        var textContent = textObject.getTextContent();
        assertNotNull(textContent);
        assertEquals(MimeType.TEXT_PLAIN, textContent.getContentType());
        assertTrue(textContent.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, textContent.getUserVisible());
        assertEquals("string", textContent.getValue().trim());
        var textObjectAttributes = textObject.getAttributes();
        assertNotNull(textObjectAttributes);
        assertNotNull(textObjectAttributes.getAttribute());
        assertEquals(1, textObjectAttributes.getAttribute().size());
        var textObjectAttribute = textObjectAttributes.getAttribute().get(0);
        assertEquals("string", textObjectAttribute.getName());
        assertEquals(MimeType.TEXT_PLAIN, textObjectAttribute.getContentType());
        assertTrue(textObjectAttribute.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, textObjectAttribute.getUserVisible());
        assertEquals("", textObjectAttribute.getClassification());
        assertFalse(textObjectAttribute.isSystemFlag());
        assertEquals("string", textObjectAttribute.getValue().trim());
        var textObjectLexiconHits = textObject.getLexiconHits();
        assertNotNull(textObjectLexiconHits);
        assertNotNull(textObjectLexiconHits.getLexiconHit());
        assertEquals(1, textObjectLexiconHits.getLexiconHit().size());
        var textObjectLexiconHit = textObjectLexiconHits.getLexiconHit().get(0);
        assertEquals("near characters", textObjectLexiconHit.getMatchType());
        assertEquals(BigInteger.valueOf(100L), textObjectLexiconHit.getNearDistance());
        assertFalse(textObjectLexiconHit.isCaseSensitive());
        assertTrue(textObjectLexiconHit.isPlainText());
        assertEquals(10L, textObjectLexiconHit.getOffset());
        assertEquals(BigInteger.valueOf(100L), textObjectLexiconHit.getLength());
        assertEquals("string", textObjectLexiconHit.getOrigin());
        assertEquals("string", textObjectLexiconHit.getCategory());
        assertEquals("string", textObjectLexiconHit.getPolicy());
        assertEquals("string", textObjectLexiconHit.getValue().trim());

        // validate action events
        var actionEvents = interaction.getActionEvents();
        assertNotNull(actionEvents);
        assertNotNull(actionEvents.getActionEvent());
        assertEquals(1, actionEvents.getActionEvent().size());
        var actionEvent = actionEvents.getActionEvent().get(0);
        assertEquals("string", actionEvent.getActionEventId());
        assertEquals("string", actionEvent.getParentActionEventId());
        assertEquals("string", actionEvent.getCocHashValue());
        assertEquals("moderate", actionEvent.getActionType());
        assertEquals("string", actionEvent.getActionSubType());
        assertEquals("string", actionEvent.getActionState());
        assertEquals("string", actionEvent.getPolicy());
        assertEquals("anySimpleType", actionEvent.getEventTime().getDescription());
        assertEquals(MimeType.TEXT_PLAIN, actionEvent.getSubject().getContentType());
        assertEquals("string", actionEvent.getSubject().getValue().trim());
        assertEquals(MimeType.TEXT_PLAIN, actionEvent.getTextContent().getContentType());
        assertEquals("string", actionEvent.getTextContent().getValue().trim());
        var actionEventActorParticipantIds = actionEvent.getActorParticipantIds();
        assertNotNull(actionEventActorParticipantIds);
        assertNotNull(actionEventActorParticipantIds.getActorParticipantId());
        assertEquals(1, actionEventActorParticipantIds.getActorParticipantId().size());
        var actionEventActorParticipantId = actionEventActorParticipantIds.getActorParticipantId().get(0);
        assertEquals("string", actionEventActorParticipantId);
        var actionEventAssigneeParticipantIds = actionEvent.getAssigneeParticipantIds();
        assertNotNull(actionEventAssigneeParticipantIds);
        assertNotNull(actionEventAssigneeParticipantIds.getAssigneeParticipantId());
        assertEquals(1, actionEventAssigneeParticipantIds.getAssigneeParticipantId().size());
        var actionEventAssigneeParticipantId = actionEventAssigneeParticipantIds.getAssigneeParticipantId().get(0);
        assertEquals("string", actionEventAssigneeParticipantId);
        var actionEventAttributes = actionEvent.getAttributes();
        assertNotNull(actionEventAttributes);
        assertNotNull(actionEventAttributes.getAttribute());
        assertEquals(1, actionEventAttributes.getAttribute().size());
        var actionEventAttribute = actionEventAttributes.getAttribute().get(0);
        assertEquals("string", actionEventAttribute.getName());
        assertEquals(MimeType.TEXT_PLAIN, actionEventAttribute.getContentType());
        assertTrue(actionEventAttribute.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, actionEventAttribute.getUserVisible());
        assertEquals("", actionEventAttribute.getClassification());
        assertFalse(actionEventAttribute.isSystemFlag());
        assertEquals("string", actionEventAttribute.getValue().trim());

        // validate policy events
        var policyEvents = interaction.getPolicyEvents();
        assertNotNull(policyEvents);
        assertNotNull(policyEvents.getPolicyEvent());
        assertEquals(1, policyEvents.getPolicyEvent().size());
        var policyEvent = policyEvents.getPolicyEvent().get(0);
        assertEquals("string", policyEvent.getPolicyEventId());
        assertFalse(policyEvent.isHistoryFlag());
        assertEquals("string", policyEvent.getCocHashValue());
        assertEquals("string", policyEvent.getPolicyEventType());
        assertEquals("string", policyEvent.getPolicyEventSubType());
        assertEquals("moderated content", policyEvent.getPolicyEventCategory());
        assertEquals("anySimpleType", policyEvent.getEventTime().getDescription());
        assertEquals(964833888000L, DateTimeUtils.toEpochMillis(policyEvent.getEventTime().getTimestamp()));
        var policyEventTriggeringParticipantIds = policyEvent.getTriggeringParticipantIds();
        assertNotNull(policyEventTriggeringParticipantIds);
        assertNotNull(policyEventTriggeringParticipantIds.getTriggeringParticipantId());
        assertEquals(1, policyEventTriggeringParticipantIds.getTriggeringParticipantId().size());
        assertEquals("string", policyEventTriggeringParticipantIds.getTriggeringParticipantId().get(0).trim());
        assertEquals("string", policyEvent.getPolicy().trim());
        var policyEventActionEventIds = policyEvent.getActionEventIds();
        assertNotNull(policyEventActionEventIds);
        assertNotNull(policyEventActionEventIds.getActionEventId());
        assertEquals(1, policyEventActionEventIds.getActionEventId().size());
        var policyEventActionEventId = policyEventActionEventIds.getActionEventId().get(0);
        assertEquals("string", policyEventActionEventId.trim());
        var policyEventTriggeringTextEventIds = policyEvent.getTriggeringTextEventIds();
        assertNotNull(policyEventTriggeringTextEventIds);
        assertNotNull(policyEventTriggeringTextEventIds.getTextEventId());
        assertEquals(1, policyEventTriggeringTextEventIds.getTextEventId().size());
        var policyEventTriggeringTextEventId = policyEventTriggeringTextEventIds.getTextEventId().get(0);
        assertEquals("string", policyEventTriggeringTextEventId.trim());
        var policyEventTriggeringFileEventIds = policyEvent.getTriggeringFileEventIds();
        assertNotNull(policyEventTriggeringFileEventIds);
        assertNotNull(policyEventTriggeringFileEventIds.getFileEventId());
        assertEquals(1, policyEventTriggeringFileEventIds.getFileEventId().size());
        var policyEventTriggeringFileEventId = policyEventTriggeringFileEventIds.getFileEventId().get(0);
        assertEquals("string", policyEventTriggeringFileEventId.trim());
        assertEquals(MimeType.TEXT_PLAIN, policyEvent.getTextContent().getContentType());
        assertEquals("string", policyEvent.getTextContent().getValue().trim());
        var policyEventAttributes = actionEvent.getAttributes();
        assertNotNull(policyEventAttributes);
        assertNotNull(policyEventAttributes.getAttribute());
        assertEquals(1, policyEventAttributes.getAttribute().size());
        var policyEventAttribute = policyEventAttributes.getAttribute().get(0);
        assertEquals("string", policyEventAttribute.getName());
        assertEquals(MimeType.TEXT_PLAIN, policyEventAttribute.getContentType());
        assertTrue(policyEventAttribute.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, policyEventAttribute.getUserVisible());
        assertEquals("", policyEventAttribute.getClassification());
        assertFalse(policyEventAttribute.isSystemFlag());
        assertEquals("string", policyEventAttribute.getValue().trim());

        // validate post attributes
        var postAttributes = interaction.getPostattributes();
        assertNotNull(postAttributes);
        assertNotNull(postAttributes.getAttribute());
        assertEquals(1, postAttributes.getAttribute().size());
        var postAttribute = postAttributes.getAttribute().get(0);
        assertEquals("string", postAttribute.getName());
        assertEquals(MimeType.TEXT_PLAIN, postAttribute.getContentType());
        assertTrue(postAttribute.isIndexable());
        assertEquals(UserVisibility.ALWAYS_VISIBLE, postAttribute.getUserVisible());
        assertEquals("", postAttribute.getClassification());
        assertFalse(postAttribute.isSystemFlag());
        assertEquals("string", postAttribute.getValue().trim());

        assertEquals("string", interaction.getElementCocMasterHashValue());
        assertEquals(10L, interaction.getNativeDataSize());

        // validate the policy
        var policy = t.getPolicy();
        assertNotNull(policy);
        var policyRetention = policy.getRetention();
        assertNotNull(policyRetention);
        var policyRetentionPolicies = policyRetention.getPolicy();
        assertNotNull(policyRetentionPolicies);
        assertEquals(1, policyRetentionPolicies.size());
        assertEquals("string", policyRetentionPolicies.get(0).getAction());
        assertEquals("anySimpleType", policyRetentionPolicies.get(0).getDecription());
        assertEquals("string", policyRetentionPolicies.get(0).getId());
        assertEquals("anyType", policy.getDisposition().trim());
    }

    static Stream<Arguments> validFileLister() {
        return Stream.of(
                Arguments.of("bloomberg.xml", true),
                Arguments.of("invalid.xml", false),
                Arguments.of("email.xml", true)
        );
    }

    // unit tests to get higher coverage

}
