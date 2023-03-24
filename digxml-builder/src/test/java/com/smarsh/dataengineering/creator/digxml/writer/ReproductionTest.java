package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import com.smarsh.dataengineering.creator.digxml.builder.*;
import com.smarsh.dataengineering.creator.digxml.builder.TranscriptPolicyBuilder.PolicyBuilder;
import com.smarsh.dataengineering.creator.digxml.builder.TranscriptPolicyBuilder.RetentionBuilder;
import com.smarsh.dataengineering.model.digxml.*;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import javax.xml.stream.XMLOutputFactory;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

// we re-create the sample `generated-transcript.xml` in the digxml-model project and verify our output in the same way
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReproductionTest {
    @TempDir
    static Path tempDir;

    static Path outputPath;

    @BeforeAll
    static void before() {
        outputPath = tempDir.resolve("foo.xml");
    }

    private static final String ANY_SIMPLE_TYPE = "anySimpleType";
    private static final String STRING = "string";

    @Test
    @Order(1)
    void reproduceSample() throws Exception {
        var participant = new ParticipantBuilder(
                new NetworkEndpointBuilder(STRING, STRING)
                        .with(neb -> {
                            neb.setDisplayName(STRING);
                            neb.setEndpointIdType("login");
                            neb.addGroup(STRING);
                        })
                        .build(),
                new UserInfoBuilder("internal")
                        .with(uib -> {
                            uib.setUserId(STRING);
                            uib.setEmailAddress(STRING);
                            uib.setName(
                                    new NameTypeBuilder()
                                            .with(ntb -> {
                                                ntb.setFirst(STRING);
                                                ntb.setMiddle(STRING);
                                                ntb.setLast(STRING);
                                                ntb.setInitials(STRING);
                                                ntb.setDisplayName(STRING);
                                            }).build()
                            );
                            uib.setAffiliation(
                                    new AffiliationBuilder()
                                            .with(ab -> {
                                                ab.setEmployeeId(STRING);
                                                ab.setTitle(STRING);
                                                ab.setDepartment(STRING);
                                                ab.setDivision(STRING);
                                                ab.setCompany(STRING);
                                                ab.setBuilding(STRING);
                                            }).build()
                            );
                            uib.setGeoLocation(
                                    new GeoLocationBuilder()
                                            .with(glb -> {
                                                glb.setCity(STRING);
                                                glb.setState(STRING);
                                                glb.setCountry(STRING);
                                            }).build()
                            );
                            uib.setPhoneNumbers(
                                    new PhoneNumbersBuilder()
                                            .with(pnb -> {
                                                pnb.setOfficePhone(STRING);
                                                pnb.setSecondaryOfficePhone(STRING);
                                                pnb.setMobilePhone(STRING);
                                                pnb.setHomePhone(STRING);
                                                pnb.setOtherPhone(STRING);
                                            }).build()
                            );
                            uib.addSecondaryAddress(
                                    new SecondaryAddressBuilder("sip")
                                            .with(sab -> sab.setValue(STRING))
                                            .build()
                            );
                            uib.addGroup(STRING);
                            uib.setImage(
                                    new ImageBuilder("jpeg")
                                            .with(ib -> ib.setValue(Base64.getDecoder().decode("ZnJlbXVudA==")))
                                            .build()
                            );
                            uib.setModificationTime(
                                    new TimeStampBuilder(1347541234000L)
                                            .with(tsb -> tsb.setDescription(ANY_SIMPLE_TYPE))
                                            .build()
                            );
                        }).build(),
                STRING
        ).with(pb -> {
            pb.addGeneralAttribute(
                    new GeneralAttributeBuilder(STRING)
                            .with(gab -> {
                                gab.setContentType(MimeType.TEXT_PLAIN.value());
                                gab.setValue(STRING);
                            })
                            .build()
            );
        }).build();

        var interaction = new InteractionBuilder(
                new TimeFrameBuilder(
                        new TimeStampBuilder(1411082313000L)
                                .with(t -> t.setDescription(ANY_SIMPLE_TYPE))
                                .build(),
                        new TimeStampBuilder(1156008434000L)
                                .with(t -> t.setDescription(ANY_SIMPLE_TYPE))
                                .build()
                ).build(),
                STRING,
                new ModalityBuilder(STRING, STRING)
                        .with(mb -> {
                            mb.setClassification(ModalityClass.SOCIAL);
                            mb.setType(ModalityType.PRIVATE);
                            mb.setVendor(STRING);
                            mb.setDescription(STRING);
                        }).build(),
                STRING
        ).with(i -> {
            i.setHistoricalDataFlag(false);
            i.setDataState("valid");
            i.setCocHashAlgorithm("sha1");
            i.setCocHashEncoding("hex");
            i.setCocHashType("full");
            i.setAttributeCocMasterHashValue(STRING);
            i.setCocHashValue(STRING);
            i.setCocVersion(STRING);
            i.setParentCommunicationObjectId(STRING);
            i.setThreadObjectId(STRING);
            i.setResourceLocation(STRING);
            i.setSubject(STRING, MimeType.TEXT_PLAIN);
            i.addAttribute(
                    new IndexableAttributeBuilder(STRING)
                            .with(iab -> {
                                iab.setContentType(MimeType.TEXT_PLAIN);
                                iab.setIndexable(true);
                                iab.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                iab.setClassification("");
                                iab.setSystemFlag(false);
                                iab.setValue("%s\n".formatted(STRING));
                            }).build()
            );
            i.addParticipant(participant);
            i.addFileEvent(
                    new FileEventBuilder(
                            new FileObjectBuilder(STRING)
                                    .with(fob -> {
                                        fob.setParentFileObjectId(STRING);
                                        fob.setParticipantId(STRING);
                                        fob.setFileObjectUrl("http://www.company.edu/profundum/bella");
                                        fob.setDuration(10L);
                                        fob.setFileContent(
                                                new FileContentBuilder()
                                                        .with(fcb -> {
                                                            fcb.setFilePath("http://www.any.edu/ac/hoc");
                                                            fcb.setName(STRING);
                                                            fcb.setContentType(MimeType.TEXT_PLAIN.value());
                                                            fcb.setEncoding(Encoding.BASE_64);
                                                            fcb.setIndexable(true);
                                                            fcb.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                                            fcb.setValue(Base64.getDecoder().decode("cmFwaWR1bQ=="));
                                                        }).build()
                                        );
                                        fob.setEventTime(
                                                new TimeStampBuilder(1455980189000L)
                                                        .with(tsb -> tsb.setDescription(ANY_SIMPLE_TYPE))
                                                        .build()
                                        );
                                        fob.addAttribute(
                                                new IndexableAttributeBuilder(STRING)
                                                        .with(iab -> {
                                                            iab.setContentType(MimeType.TEXT_PLAIN);
                                                            iab.setIndexable(true);
                                                            iab.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                                            iab.setClassification("");
                                                            iab.setSystemFlag(false);
                                                            iab.setValue(STRING);
                                                        }).build()
                                        );
                                        fob.addLexiconHit(
                                                new LexiconHitBuilder("near characters")
                                                        .with(lhb -> {
                                                            lhb.setValue("%s\n".formatted(STRING));
                                                            lhb.setNearDistance(BigInteger.valueOf(100L));
                                                            lhb.setCaseSensitive(false);
                                                            lhb.setPlainText(true);
                                                            lhb.setOffset(10L);
                                                            lhb.setLength(BigInteger.valueOf(100L));
                                                            lhb.setOrigin(STRING);
                                                            lhb.setCategory(STRING);
                                                            lhb.setPolicy(STRING);
                                                        }).build()
                                        );
                                    })
                                    .build(),
                            STRING,
                            FileEventType.BODY
                    ).with(feb -> {
                        feb.setCocHashValue(STRING);
                    }).build()
            );
            i.addTextEvent(
                    new TextEventBuilder(
                            new TextObjectBuilder(
                                    STRING,
                                    TextEventType.COMMENT,
                                    new TimeStampBuilder(1440371363000L).with(tob -> tob.setDescription(ANY_SIMPLE_TYPE)).build(),
                                    STRING
                            ).with(tob -> {
                                tob.setParentTextObjectId(STRING);
                                tob.setSystemFlag(false);
                                tob.setTextObjectType(STRING);
                                tob.setTextObjectSubType(STRING);
                                tob.setTextObjectUrl("http://www.any.com/claustra/circum");
                                tob.setLinkCommunicationObjectId(STRING);
                                tob.addFileEventId(STRING);
                                tob.setTextContent(
                                        new TextContentBuilder()
                                                .with(tcb -> {
                                                    tcb.setValue("\n%s\n".formatted(STRING));
                                                    tcb.setIndexable(true);
                                                    tcb.setUserVisibile(UserVisibility.ALWAYS_VISIBLE);
                                                }).build()
                                );
                                tob.addAttribute(
                                        new IndexableAttributeBuilder(STRING)
                                                .with(iab -> {
                                                    iab.setContentType(MimeType.TEXT_PLAIN);
                                                    iab.setIndexable(true);
                                                    iab.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                                    iab.setClassification("");
                                                    iab.setSystemFlag(false);
                                                    iab.setValue("%s\n".formatted(STRING));
                                                }).build()
                                );
                                tob.addLexiconHit(
                                        new LexiconHitBuilder("near characters")
                                                .with(lhb -> {
                                                    lhb.setNearDistance(BigInteger.valueOf(100L));
                                                    lhb.setCaseSensitive(false);
                                                    lhb.setPlainText(true);
                                                    lhb.setOffset(10L);
                                                    lhb.setOrigin(STRING);
                                                    lhb.setCategory(STRING);
                                                    lhb.setPolicy(STRING);
                                                    lhb.setValue("%s\n".formatted(STRING));
                                                    lhb.setLength(BigInteger.valueOf(100L));
                                                }).build()
                                );
                            }).build(),
                            STRING
                    ).with(teb -> {
                        teb.setOperation(STRING);
                        teb.setHistoryFlag(false);
                        teb.setCocHashValue(STRING);
                    }).build()
            );
            i.addActionEvent(
                    new ActionEventBuilder("moderate", STRING)
                            .with(aeb -> {
                                aeb.setActionState(STRING);
                                aeb.setPolicy(STRING);
                                aeb.setEventTime(
                                        new TimeStampBuilder(0L)
                                                .with(tsb -> tsb.setDescription(ANY_SIMPLE_TYPE))
                                                .build()
                                );
                                aeb.setParentActionEventId(STRING);
                                aeb.setCocHashValue(STRING);
                                aeb.setActionSubType(STRING);
                                aeb.setSubject(new SubjectBuilder(STRING, MimeType.TEXT_PLAIN).build());
                                aeb.setTextContent(new ActionEventBuilder.TextContentBuilder(STRING, MimeType.TEXT_PLAIN).build());
                                aeb.addActorParticipantId(STRING);
                                aeb.addAssigneeParticipantId(STRING);
                                aeb.addAttribute(
                                        new IndexableAttributeBuilder(STRING)
                                                .with(iab -> {
                                                    iab.setContentType(MimeType.TEXT_PLAIN);
                                                    iab.setIndexable(true);
                                                    iab.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                                    iab.setClassification("");
                                                    iab.setSystemFlag(false);
                                                    iab.setValue(STRING);
                                                }).build()
                                );
                            }).build()
            );
            i.addPolicyEvent(
                    new PolicyEventBuilder(STRING, "moderated content", STRING)
                            .with(peb -> {
                                peb.setCocHashValue(STRING);
                                peb.setPolicyEventSubType(STRING);
                                peb.setHistoryFlag(false);
                                peb.setEventTime(
                                        new TimeStampBuilder(964833888000L)
                                                .with(tsb -> tsb.setDescription(ANY_SIMPLE_TYPE))
                                                .build()
                                );
                                peb.addTriggeringParticipantId(STRING);
                                peb.setPolicy(STRING);
                                peb.addActionEventId(STRING);
                                peb.addTriggeringTextEventId(STRING);
                                peb.addTriggeringFileEventId(STRING);
                                peb.setTextContent(
                                        new PolicyEventBuilder.TextContentBuilder()
                                                .with(tcb -> {
                                                    tcb.setValue(STRING);
                                                    tcb.setContentType(MimeType.TEXT_PLAIN);
                                                }).build()
                                );
                                peb.addAttribute(
                                        new IndexableAttributeBuilder(STRING)
                                                .with(iab -> {
                                                    iab.setContentType(MimeType.TEXT_PLAIN);
                                                    iab.setIndexable(true);
                                                    iab.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                                    iab.setClassification("");
                                                    iab.setSystemFlag(false);
                                                    iab.setValue("%s\n".formatted(STRING));
                                                }).build()
                                );
                            }).build()
            );
            i.addPostAttribute(
                    new IndexableAttributeBuilder(STRING)
                            .with(iab -> {
                                iab.setContentType(MimeType.TEXT_PLAIN);
                                iab.setIndexable(true);
                                iab.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                iab.setClassification("");
                                iab.setSystemFlag(false);
                                iab.setValue("%s\n".formatted(STRING));
                            }).build()
            );
            i.setElementCocMasterHashValue(STRING);
            i.setNativeDataSize(10L);
        }).build();

        var transcript = new TranscriptBuilder(
                interaction,
                STRING,
                STRING,
                DateTimeUtils.toXmlGregorian(1222652985000L)
        ).with(tb -> {
            tb.setTranscriptPolicy(
                    new TranscriptPolicyBuilder(
                            new RetentionBuilder()
                                    .with(rb ->
                                            rb.addPolicy(
                                                    new PolicyBuilder(STRING)
                                                            .with(pb -> {
                                                                pb.setDescription(ANY_SIMPLE_TYPE);
                                                                pb.setId(STRING);
                                                            })
                                                            .build()
                                            )
                                    ).build(),
                            "anyType"
                    ).build()
            );
            tb.setSourceEndpointVersion(STRING);
        }).build();

        var outputWriter = new FileWriter(outputPath.toFile());
        var xmlStreamWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(outputWriter);

        var digXmlWriter = new DigXmlWriter(new IndentingXMLStreamWriter(xmlStreamWriter));
        digXmlWriter.writeDigXml(transcript);

        xmlStreamWriter.flush();
        xmlStreamWriter.close();

        outputWriter.flush();
        outputWriter.close();

        System.out.println(Files.readString(outputPath));
    }


    @Test
    @Order(2)
    void validateContent() throws Exception {

        JAXBContext context = JAXBContext.newInstance(Transcript.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        var t = (Transcript) unmarshaller.unmarshal(new FileReader(outputPath.toFile()));

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
        assertEquals(1156008434000L, DateTimeUtils.toEpochMillis(interactionTimeFrame.getEndTime().getTimestamp()), "interaction.timeframe.endtime is not correct");

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

}
