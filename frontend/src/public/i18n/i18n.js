import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import { initReactI18next } from "react-i18next";

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        resources: {
            en: {
                translations: {
                    CURRENCY_USD: "American dollar",
                    CURRENCY_EUR: "Euro",
                    CURRENCY_CNY: "Chinese renminbi",
                    CURRENCY_GBP: "Pound sterling",
                    CURRENCY_JPY: "Japanese yen",
                    CURRENCY_PLN: "Polish zloty",
                    CURRENCY_RUB: "Russian ruble",
                    OPERATION_ADD: "Added (manually)",
                    OPERATION_DELETE: "Deleted (manually)",
                    "currency-rates": "Currency rates",
                    OPERATION_CONVERT: "Konwertacja",
                }
            },
            pl: {
                translations: {

                }
            }
        },
        fallbackLng: "en",
        debug: true,

        // have a common namespace used around the full app
        ns: ["translations"],
        defaultNS: "translations",

        keySeparator: false, // we use content as keys

        interpolation: {
            escapeValue: false
        }
    });

export default i18n;
