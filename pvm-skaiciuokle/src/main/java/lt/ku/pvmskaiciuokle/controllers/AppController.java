package lt.ku.pvmskaiciuokle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class AppController {

    // Suapvalinti skaičius iki nurodyto skaitmens po kablelio.
    public static double round(double sk, int t) {
        // Panaudota supaprastinta funkcija pagal `org.apache.commons.math3.util` paketo kodą.
        return (new BigDecimal(Double.toString(sk)).setScale(t, RoundingMode.HALF_UP)).doubleValue();
    }

    // Pagrindinis puslapis
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Rezultato puslapis
    @PostMapping("/result")
    public String result(
            @RequestParam(value = "kaina", defaultValue = "0") Double kaina,
            @RequestParam(value = "kiekis", defaultValue = "1") Integer kiekis,
            @RequestParam(value = "tarifas", defaultValue = "21") Integer tarifas,
            Model model
    ) {

        // Kaip skaičiuoti PVM?
        // https://www.kaip.lt/darbas-verslas/kaip-apskaiciuoti-pvm-nuo-prekes-kainos-be-ir-su-pvm/

        // Suapvalinti įvestą kainą iki 2 skaičių po kablelio.
        kaina = round(kaina, 2);

        double vienetoPvm = round(kaina * tarifas / (100.0 + tarifas), 2);
        double vienetoKainaBePvm = round(kaina / ((100.0 + tarifas) / 100.0), 2);
        double bendraKainaBePvm = vienetoKainaBePvm * kiekis;
        double visaSuma = kaina * kiekis;
        double bendrasPvm = vienetoPvm * kiekis;

        // Pridėti atributų reikšmes į Thymeleaf šabloną.
        model.addAttribute("vienetoKainaSuPvm", String.format("%.2f", kaina));
        model.addAttribute("vienetoPvm", String.format("%.2f", vienetoPvm));
        model.addAttribute("vienetoKainaBePvm", String.format("%.2f", vienetoKainaBePvm));
        model.addAttribute("bendraKainaBePvm", String.format("%.2f", bendraKainaBePvm));
        model.addAttribute("visaSuma", String.format("%.2f", visaSuma));
        model.addAttribute("bendrasPvm", String.format("%.2f", bendrasPvm));

        return "result";
    }
}
