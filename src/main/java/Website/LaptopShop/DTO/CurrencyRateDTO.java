package Website.LaptopShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyRateDTO
{
    private String from;
    private String to;
    private String value;
}

