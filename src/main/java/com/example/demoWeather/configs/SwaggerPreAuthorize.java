package com.example.demoWeather.configs;

import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;


@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class SwaggerPreAuthorize implements OperationBuilderPlugin {
    @Override
    public void apply(final OperationContext context) {
        // Look for @PreAuthorize on method, otherwise look on controller
        context.findAnnotation(PreAuthorize.class)
                .or(() -> context.findControllerAnnotation(PreAuthorize.class))
                .ifPresent(preAuth -> context.operationBuilder()
                        .notes(getNotes(preAuth)));
    }

    private String getNotes(PreAuthorize preAuth) {
        String preAuthExpr = preAuth.value();
        return "**Security Authorize expression:** `" + preAuthExpr + "`";
    }

    @Override
    public boolean supports(@NonNull final DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}