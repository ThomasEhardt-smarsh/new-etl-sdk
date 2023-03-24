package com.smarsh.dataengineering.conversionsdk.util;

import com.smarsh.dataengineering.conversionsdk.util.EmailParser.EmailUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmailParserTest {

    @Test
    void dedupUsersByEmailAddress() {
        var standardEmailUsers = List.of(
                new EmailUser("john.doe@example.com", "John Doe"),
                new EmailUser("jane.doe@example.com", "Jane Doe"),
                new EmailUser("mary.moe@example.com", "Mary Moe")
        );
        assertEquals(3, EmailUser.dedupUsersByEmailAddress(standardEmailUsers, false).size());

        var standardEmailUsersWithCaseDupes = List.of(
                new EmailUser("john.doe@example.com", "John Doe"),
                new EmailUser("jane.doe@example.com", "Jane Doe"),
                new EmailUser("mary.moe@example.com", "Mary Moe"),
                new EmailUser("JOHN.DOE@EXAMPLE.COM", "JOHN DOE"),
                new EmailUser("JANE.DOE@EXAMPLE.COM", "JANE DOE"),
                new EmailUser("MARY.MOE@EXAMPLE.COM", "MARY MOE")
        );
        var standardEmailUsersWithCaseDupesReverse = List.of(
                new EmailUser("JOHN.DOE@EXAMPLE.COM", "JOHN DOE"),
                new EmailUser("JANE.DOE@EXAMPLE.COM", "JANE DOE"),
                new EmailUser("MARY.MOE@EXAMPLE.COM", "MARY MOE"),
                new EmailUser("john.doe@example.com", "John Doe"),
                new EmailUser("jane.doe@example.com", "Jane Doe"),
                new EmailUser("mary.moe@example.com", "Mary Moe")
        );


        var dedupedUsersRegularOrder = EmailUser.dedupUsersByEmailAddress(standardEmailUsersWithCaseDupes, true);

        assertEquals(6, EmailUser.dedupUsersByEmailAddress(standardEmailUsersWithCaseDupes, false).size());
        assertEquals(3, dedupedUsersRegularOrder.size());

        // based on the order input, names and email addresses should all be lower case
        var dedupedAddressesRegularOrder = dedupedUsersRegularOrder.stream()
                .map(EmailUser::address)
                .toList();

        assertEquals(3, dedupedAddressesRegularOrder.size());
        assertTrue(dedupedAddressesRegularOrder.containsAll(List.of(
                    "john.doe@example.com",
                    "jane.doe@example.com",
                    "mary.moe@example.com"
            ))
        );

        var dedupedNamesRegularOrder = dedupedUsersRegularOrder.stream()
                .map(EmailUser::name)
                .toList();
        assertEquals(3, dedupedNamesRegularOrder.size());
        assertTrue(dedupedNamesRegularOrder.containsAll(List.of(
                        "John Doe",
                        "Jane Doe",
                        "Mary Moe"
                ))
        );

        // based on reversing the order input, names and email addresses should all be lower case
        var dedupedUsersReverseOrder = EmailUser.dedupUsersByEmailAddress(standardEmailUsersWithCaseDupesReverse, true);
        var dedupedAddressesReverseOrder = dedupedUsersReverseOrder.stream()
                .map(EmailUser::address)
                .toList();

        assertEquals(3, dedupedAddressesReverseOrder.size());
        assertTrue(dedupedAddressesReverseOrder.containsAll(List.of(
                "JOHN.DOE@EXAMPLE.COM",
                "JANE.DOE@EXAMPLE.COM",
                "MARY.MOE@EXAMPLE.COM"
                ))
        );

        var dedupedNamesReverseOrder = dedupedUsersReverseOrder.stream()
                .map(EmailUser::name)
                .toList();
        assertEquals(3, dedupedNamesReverseOrder.size());
        assertTrue(dedupedNamesReverseOrder.containsAll(List.of(
                "JOHN DOE",
                "JANE DOE",
                "MARY MOE"
                ))
        );
    }

    @ParameterizedTest
    @MethodSource
    void emailUserFromString(String address, Boolean shouldParse) {
        var emailUser = EmailUser.fromString(address);

        assertEquals(shouldParse, emailUser.isPresent());
        if (shouldParse) {
            assertEquals(address, emailUser.get().address());
        }
    }

    public static Stream<Arguments> emailUserFromString() {
        return Stream.of(
                Arguments.arguments("john.doe@example.com", true),
                Arguments.arguments("this isn't a valid address", false),
                Arguments.arguments("3128675309", true)
        );
    }
}