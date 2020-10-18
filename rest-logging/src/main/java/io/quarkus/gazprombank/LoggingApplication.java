package io.quarkus.gazprombank;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(title = "Logging API",
        description = "This API allows CRUD operations on a custom data that could be logged",
        version = "1.0",
        contact = @Contact(name = "Ilya Ryabukhin", url = "github.com/ilia2108")),
    servers = {
        @Server(url = "http://localhost:8083")
    },
    tags = {
        @Tag(name = "api", description = "Public that can be used by anybody"),
        @Tag(name = "logs")
    }
)
public class LoggingApplication extends Application {
}