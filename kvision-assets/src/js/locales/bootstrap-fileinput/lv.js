/*!
 * FileInput Latvian Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author Uldis Nelsons <uldisnelsons@gmail.com>
 *
 * NOTE: this file must be saved in UTF-8 encoding.
 */
(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof module === 'object' && typeof module.exports === 'object') {
        factory(require('jquery'));
    } else {
        factory(window.jQuery);
    }
}(function ($) {
    "use strict";

    $.fn.fileinputLocales['lv'] = {
        sizeUnits: ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'], 
        bitRateUnits: ['B/s', 'KB/s', 'MB/s', 'GB/s', 'TB/s', 'PB/s', 'EB/s', 'ZB/s', 'YB/s'],
        fileSingle: 'failu',
        filePlural: 'faili',
        browseLabel: 'Izvēlaties &hellip;',
        removeLabel: 'Dzēst',
        removeTitle: 'Notīrīt izvēlētos failus',
        cancelLabel: 'Atcelt',
        cancelTitle: 'Atcelt pašreizējo augšupielādi',
        uploadLabel: 'Augšuplādēt',
        uploadTitle: 'Augšuplādēt izvēlētos failus',
        msgNo: 'nē',
        msgNoFilesSelected: '',
        msgCancelled: 'Atcelts',
        msgPlaceholder: 'Izvēlēties {files}...',
        msgZoomModalHeading: 'Detalizēts priekšskatījums',
        msgFileRequired: 'Jums jāizvēlas augšupielādējamais fails..',
        msgSizeTooSmall: 'Fails "{name}" (<b>{size}</b>) ir pārāk mazs un tam jābūt lielākam <b>{minSize}</b>.',
        msgSizeTooLarge: 'Fails "{name}" (<b>{size}</b>) pārsniedz maksimālo izmēru <b>{maxSize}</b>.',
        msgFilesTooLess: 'Lai lejupielādētu, jums jāizvēlas vismaz <b> {n} </b> {files}.',
        msgFilesTooMany: 'Izvēlēto failu skaits <b> ({n}) </b> pārsniedz maksimālo atļauto skaitu <b>{m}</b>.',
        msgFileNotFound: 'Fails "{name}" nav atrasts',
        msgFileSecured: 'Drošības ierobežojumi aizliedz lasīt failu "{name}".',
        msgFileNotReadable: 'Failu "{name}" nevar nolasīt.',
        msgFilePreviewAborted: 'Priekšskatījums atcelts failam "{name}".',
        msgFilePreviewError: 'Lasot failu "{name}", radās kļūda.',
        msgInvalidFileName: 'Nederīgas vai neatbalstītas rakstzīmes faila nosaukumā "{name}".',
        msgInvalidFileType: 'Neatļauts faila tips "{name}". Ir atļauti tikai "{types}".',
        msgInvalidFileExtension: 'Faila aizliegtais paplašinājums ir "{name}". Ir atļauti tikai "{extensions}".',
        msgFileTypes: {
            'image': 'image',
            'html': 'HTML',
            'text': 'text',
            'video': 'video',
            'audio': 'audio',
            'flash': 'flash',
            'pdf': 'PDF',
            'object': 'object'
        },
        msgUploadAborted: 'Failu augšupielāde tiek pārtraukta',
        msgUploadThreshold: 'Apstrāde...',
        msgUploadBegin: 'Inicializēšana...',
        msgUploadEnd: 'Gatavs',
        msgUploadEmpty: 'Neatļauti lejupielādes dati',
        msgUploadError: 'Augšuplādēšanas kļūda',
        msgValidationError: 'Validācijas kļūda',
        msgLoading: 'Lejupielādē failu {index} no {files} &hellip;',
        msgProgress: 'Notiek faila {index} ielāde no {files} - {name} - pabeigts {percent}%.',
        msgSelected: 'Atlasīti faili: {n}',
        msgProcessing: 'Processing ...',
        msgFoldersNotAllowed: 'Atļauts ievilkt un noņemt vienīgi failus! Trūkst {n} mapes.',
        msgImageWidthSmall: 'Attēla platumam {name} jābūt vismaz <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageHeightSmall: 'Attēla augstumam {name} jābūt vismaz <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageWidthLarge: 'Attēla platums "{name}" nevar pārsniegt <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageHeightLarge: 'Attēla "{name}" augstums nevar pārsniegt <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageResizeError: 'Nevar mainīt attēlu, jo neizdevās iegūt tā izmērus',
        msgImageResizeException: 'Kļūda, mainot attēla izmērus.<pre>{errors}</pre>',
        msgAjaxError: 'Operācijas laikā {darbība} radās kļūda. Vēlāk mēģiniet vēlreiz!',
        msgAjaxProgressError: 'Neizdevās izpildīt {operation}',
        ajaxOperations: {
            deleteThumb: 'dzēst failu',
            uploadThumb: 'augšuplādēt failu',
            uploadBatch: 'augšuplādēt mapi',
            uploadExtra: 'augšuplādēt formas datus'
        },
        dropZoneTitle: 'Ievelc šeit failus &hellip;',
        dropZoneClickTitle: '<br>(Vai arī noklikšķiniet, lai izvēlētos {files})',
        fileActionSettings: {
            removeTitle: 'Dzēst failu',
            uploadTitle: 'Augšuplādēt failu',
            uploadRetryTitle: 'Atkārtot augšuplādēšanu',
            downloadTitle: 'Lejuplādēt failu',
            rotateTitle: 'Rotate 90 deg. clockwise',
            zoomTitle: 'Pārskatīt detaļas',
            dragTitle: 'Pārvietot / Mainīt secību',
            indicatorNewTitle: 'Vēl nav augšuplādēts',
            indicatorSuccessTitle: 'Ir augšuplādēts',
            indicatorErrorTitle: 'Augšuplādēšanas kļūda',
            indicatorLoadingTitle: 'Augšuplādēšana ...'
        },
        previewZoomButtonTitles: {
            prev: 'Skatīt iepriekšējo failu',
            next: 'Skatīt nākamo failu',
            rotate: 'Rotate 90 deg. clockwise',
            toggleheader: 'Skatīt nākamo failu',
            fullscreen: 'Pārslēgt pilnekrāna režīmu',
            borderless: 'Pārslēgt bez kontūrām',
            close: 'Aizveriet detalizētu priekšskatījumu'
        }
    };
}));
