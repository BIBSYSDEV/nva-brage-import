package no.unit.nva.importbrage.metamodel;


import no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException;
import no.unit.nva.importbrage.metamodel.types.ContributorType;
import no.unit.nva.model.Contributor;
import no.unit.nva.model.Identity;
import no.unit.nva.model.NameType;
import no.unit.nva.model.Role;
import no.unit.nva.model.exceptions.MalformedContributorException;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.AbstractMap;
import java.util.stream.Stream;

import static no.unit.nva.importbrage.metamodel.exceptions.UnknownRoleMappingException.MESSAGE_TEMPLATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

// This test will be removed once the mappings are in place as it will be covered by a test in XmlImportTest.

class BrageContributorTest {
    public static final String ANY_NAME = "Nameson, My";

    @ParameterizedTest(name = "Brage Contributor with contributor type {0} can be mapped to an NVA role")
    @MethodSource("contributorMappingPairs")
    void getNvaContributorReturnsNvaContributorWhenInputMappingIsKnown(
            AbstractMap.SimpleEntry<ContributorType, Role> value)
            throws MalformedContributorException, UnknownRoleMappingException {
        var contributorType = value.getKey();
        var actual = new BrageContributor(contributorType, ANY_NAME).getNvaContributor();
        Contributor expected = generateNvaContributor(value.getValue(), ANY_NAME);
        assertThat(actual, equalTo(expected));
    }

    @ParameterizedTest(name = "Brage Contributor with contributor type {0} can be mapped to an NVA role")
    @MethodSource("unknownContributorMappingPairs")
    void getNvaContributorThrowsUnknownRoleMappingExceptionWhenInputMappingIsUnknown(ContributorType contributorType) {
        Executable executable = () -> new BrageContributor(contributorType, ANY_NAME).getNvaContributor();
        var actual = assertThrows(UnknownRoleMappingException.class, executable);
        String expected = String.format(MESSAGE_TEMPLATE, contributorType.getTypeName());
        assertThat(actual.getMessage(), equalTo(expected));
    }

    private Contributor generateNvaContributor(Role role, String name) throws MalformedContributorException {
        var identity = new Identity.Builder()
                .withName(name)
                .withNameType(NameType.PERSONAL)
                .build();
        return new Contributor.Builder()
                .withIdentity(identity)
                .withRole(role)
                .build();
    }

    private static Stream<Arguments> contributorMappingPairs() {
        return Stream.of(
                Arguments.of(new AbstractMap.SimpleEntry<>(ContributorType.ADVISOR, Role.ADVISOR)),
                Arguments.of(new AbstractMap.SimpleEntry<>(ContributorType.EDITOR, Role.EDITOR)),
                Arguments.of(new AbstractMap.SimpleEntry<>(ContributorType.ILLUSTRATOR, Role.ILLUSTRATOR)),
                Arguments.of(new AbstractMap.SimpleEntry<>(ContributorType.AUTHOR, Role.CREATOR)),
                Arguments.of(new AbstractMap.SimpleEntry<>(ContributorType.UNQUALIFIED, Role.CREATOR))
        );
    }

    private static Stream<Arguments> unknownContributorMappingPairs() {
        return Stream.of(
                Arguments.of(ContributorType.DEPARTMENT),
                Arguments.of(ContributorType.ORCID),
                Arguments.of(ContributorType.OTHER)
        );
    }
}