package de.papenhagen;

import de.papenhagen.enities.Color;
import de.papenhagen.enities.RGB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import static java.util.Objects.isNull;

@Path("/color")
public class ColorResource {

    @Inject
    private ColorConvertorService colorConvertorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Output colorPicker(@QueryParam(value = "red") final Integer red,
                              @QueryParam(value = "green") final Integer green,
                              @QueryParam(value = "blue") final Integer blue) {


        final int colorR = getValidColorValueInRGBSpace(red);
        final int colorG = getValidColorValueInRGBSpace(green);
        final int colorB = getValidColorValueInRGBSpace(blue);

        final Color rgb = new RGB(colorR, colorG, colorB);
        final Color cmyk = colorConvertorService.convertRGBtoCMYK(rgb);
        final Color hls = colorConvertorService.convertRGBtoHSL(rgb);
        final Color yuv = colorConvertorService.convertRGBtoYUV(rgb);

        return new Output(List.of(rgb, cmyk, hls, yuv));
    }


    private int getValidColorValueInRGBSpace(final Integer color) {
        if (isNull(color)) {
            return 0;
        }
        if (color > 255 || color < 0) {
            return 0;

        }
        return color;
    }

    public record Output(List<Color> colors) { };
}
