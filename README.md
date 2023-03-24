# new-etl-sdk
all the things related to the new hotness for etl

This currently exists as multiple sub-projects under a single project but really should be individual projects:

* sdk-base is the "new etl-sdk-lib"
* digxml-model is the bare code necessary to work with the DIG model (e.g. classes generated from XSD files)
* digxml-creator is the code necessary to convert (any) to DIG XML, includes writers and builders
* fxconnect-transformer is the sample code for email-based FXConnect

## general notes

* I'm not beholden to any names or anything in the code - if the projects/classes could be named better, let's do it
* this was written as 4 separate projects because they should be separate - I thought about combinging the two DIG projects but since the model never changes, it should suffice on its own
* things being separate projects means that we can utilize this code elsewhere
* there is still more logging I can add (but not to, say, digxml-model)

## some notes on specific projects in random order

### sdk-base

* I tried to capture the essence of what is in etl-sdk-lib without the provider classes
* I don't believe we need the provider classes, just a common method such as `convert(InputStream, OutputStream)`
* I did not get many tests written for the sdk-base
* there's a class to base64 encode files of arbitrary size
* there is currently no code for dejournaling
* change from etl-sdk-lib: There's an `AbstractEmailParser` class which contains what I believe to be the necessary methods; it also includes a class that prevents users from having to know about how `InternetAddress` and `Address` work and such
* there's a `SimpleEmailParser` which (mostly) mimics the one in etl-sdk-lib but has some features I wanted to add, such as ensuring that if we list attachments, we are getting only things with `Content-Disposition: attachment` - there was an example from a customer where not some embedded content was not an attachment but was picked up as such


### digxml-model

* I have not been able to get JAXB to work with the classes generated in digxml-model; I don't know exactly why, but this would also really only be useful for testing, since we would expect input data to be too large to fit into memory
* there are some customizations on top of the XSD to allow for code generation, large text/file events, and the DIG namespaces
* DIG supports attachments of (virtually) unlimited size


### digxml-creator

* when writing the builder classes, I looked at the XSD - anything that is required must go in the constructor - I may change my mind on that
* rather than writing a `.setXXX` method as well as a `.withXXX` moethod for every class attribute, I opted for a single `.with` method for all optional attributes - there are examples all over about how this works in the code but basically, it's `builder.with(builder -> builder.setXXX())` - this cuts down on the necessary boilerplate code
* I don't like `TranscriptWriter` being the thing that writes the `Transcript` object as well as the whole XML - I will likely add a writer for that in the future called `DigWriter` or `DigXmlWriter`
* `TranscriptWriterTest` isn't a test so much as something I was using to validate that I could create valid DIG XML by trying to recreate one of the samples I was given
* there is certainly some more work to be done here in terms of validating that we have valid DIG XML (e.g. every chat participant must be in the participant list, similar to DRXML)
* I spoke with Jon Onusko for a bit about the checksums and such, and they are currently not used anywhere - if we need to get those at some point, that can be added later
* many of the samples I have seen specify xml version 1.1 and some specify 1.0, but my command line tools don't support 1.1 so I opted for 1.0 - I don't think this should matter either way
* there are many values that I don't yet know what they represent - such as endpointIdType for a participant - we may need multiple representations for each participant that sends a message (as sender and receiver)
* there's no pretty-print for the XML, it writes out in a single line - that shouldn't be a problem for parsing but if we want I can look at adding that

### fxconnect-converter

* the only fxconnect email samples i could find were from Credit Suisse, and they are journaled. sdk-base doesn't have this capability yet
* because of JSXB unmarshalling not working, my `convert` test uses string matching - I simply ran out of time to write the proper XML parsing code and will do that later
* none of the samples i found had attachments - if i remember correctly, fxconnect doesn't have attachments and therefore i wasted a lot of time up front writing the attachment parsing stuff
