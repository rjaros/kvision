/*!
 * FileInput Azerbaijan Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author Elbrus <elbrusnt@gmail.com>
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

    $.fn.fileinputLocales['az'] = {
        sizeUnits: ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'], 
        bitRateUnits: ['B/s', 'KB/s', 'MB/s', 'GB/s', 'TB/s', 'PB/s', 'EB/s', 'ZB/s', 'YB/s'],
        fileSingle: 'fayl',
        filePlural: 'fayl',
        browseLabel: 'Seç &hellip;',
        removeLabel: 'Sil',
        removeTitle: 'Seçilmiş faylları təmizlə',
        cancelLabel: 'İmtina et',
        cancelTitle: 'Cari yükləməni dayandır',
        pauseLabel: 'Pause',
        pauseTitle: 'Pause ongoing upload',
        uploadLabel: 'Yüklə',
        uploadTitle: 'Seçilmiş faylları yüklə',
        msgNo: 'xeyir',
        msgNoFilesSelected: 'Heç bir fayl seçilməmişdir',
        msgPaused: 'Paused',
        msgCancelled: 'İmtina edildi',
        msgPlaceholder: 'Select {files} ...',
        msgZoomModalHeading: 'İlkin baxış',
        msgFileRequired: 'Yükləmə üçün fayl seçməlisiniz.',
        msgSizeTooSmall: 'Seçdiyiniz "{name}" faylının həcmi (<b>{size}</b>)-dır,  minimum <b>{minSize}</b> olmalıdır.',
        msgSizeTooLarge: 'Seçdiyiniz "{name}" faylının həcmi (<b>{size}</b>)-dır,  maksimum <b>{maxSize}</b> olmalıdır.',
        msgFilesTooLess: 'Yükləmə üçün minimum <b>{n}</b> {files} seçməlisiniz.',
        msgFilesTooMany: 'Seçilmiş fayl sayı <b>({n})</b>. Maksimum <b>{m}</b> fayl seçmək mümkündür.',
        msgTotalFilesTooMany: 'You can upload a maximum of <b>{m}</b> files (<b>{n}</b> files detected).',
        msgFileNotFound: 'Fayl "{name}" tapılmadı!',
        msgFileSecured: '"{name}" faylının istifadəsinə yetginiz yoxdur.',
        msgFileNotReadable: '"{name}" faylının istifadəsi mümkün deyil.',
        msgFilePreviewAborted: '"{name}" faylı üçün ilkin baxış ləğv olunub.',
        msgFilePreviewError: '"{name}" faylının oxunması mümkün olmadı.',
        msgInvalidFileName: '"{name}" faylının adında qadağan olunmuş simvollardan istifadə olunmuşdur.',
        msgInvalidFileType: '"{name}" faylının tipi dəstəklənmir. Yalnız "{types}" tipli faylları yükləmək mümkündür.',
        msgInvalidFileExtension: '"{name}" faylının genişlənməsi yanlışdır. Yalnız "{extensions}" fayl genişlənmə(si / ləri) qəbul olunur.',
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
        msgUploadAborted: 'Yükləmə dayandırılmışdır',
        msgUploadThreshold: 'Yükləmə &hellip;',
        msgUploadBegin: 'Yoxlama &hellip;',
        msgUploadEnd: 'Fayl(lar) yükləndi',
        msgUploadResume: 'Resuming upload &hellip;',
        msgUploadEmpty: 'Yükləmə üçün verilmiş məlumatlar yanlışdır',
        msgUploadError: 'Upload Error',
        msgDeleteError: 'Delete Error',
        msgProgressError: 'Error',
        msgValidationError: 'Yoxlama nəticəsi səhvir',
        msgLoading: '{files} fayldan {index} yüklənir &hellip;',
        msgProgress: '{files} fayldan {index} - {name} - {percent}% yükləndi.',
        msgSelected: 'Faylların sayı: {n}',
        msgProcessing: 'Processing ...',
        msgFoldersNotAllowed: 'Ancaq faylların daşınmasına icazə verilir! {n} qovluq yüklənmədi.',
        msgImageWidthSmall: '{name} faylının eni <b>{size} px</b> (detected <b>{dimension} px</b>) -dən kiçik olmamalıdır.',
        msgImageHeightSmall: '{name} faylının hündürlüyü <b>{size} px</b> (detected <b>{dimension} px</b>) -dən kiçik olmamalıdır.',
        msgImageWidthLarge: '"{name}" faylının eni <b>{size} px</b> (detected <b>{dimension} px</b>) -dən böyük olmamalıdır.',
        msgImageHeightLarge: '"{name}" faylının hündürlüyü <b>{size} px</b> (detected <b>{dimension} px</b>) -dən böyük olmamalıdır.',
        msgImageResizeError: 'Faylın ölçülərini dəyişmək üçün ölçüləri hesablamaq mümkün olmadı.',
        msgImageResizeException: 'Faylın ölçülərini dəyişmək mümkün olmadı.<pre>{errors}</pre>',
        msgAjaxError: '{operation} əməliyyatı zamanı səhv baş verdi. Təkrar yoxlayın!',
        msgAjaxProgressError: '{operation} əməliyyatı yerinə yetirmək mümkün olmadı.',
        msgDuplicateFile: 'File "{name}" of same size "{size}" has already been selected earlier. Skipping duplicate selection.',
        msgResumableUploadRetriesExceeded:  'Upload aborted beyond <b>{max}</b> retries for file <b>{file}</b>! Error Details: <pre>{error}</pre>',
        msgPendingTime: '{time} remaining',
        msgCalculatingTime: 'calculating time remaining',
        ajaxOperations: {
            deleteThumb: 'faylı sil',
            uploadThumb: 'faylı yüklə',
            uploadBatch: 'bir neçə faylı yüklə',
            uploadExtra: 'məlumatların yüklənməsi'
        },
        dropZoneTitle: 'Faylları bura daşıyın &hellip;',
        dropZoneClickTitle: '<br>(Və ya seçin {files})',
        fileActionSettings: {
            removeTitle: 'Faylı sil',
            uploadTitle: 'Faylı yüklə',
            uploadRetryTitle: 'Retry upload',
            downloadTitle: 'Download file',
            rotateTitle: 'Rotate 90 deg. clockwise',
            zoomTitle: 'məlumatlara bax',
            dragTitle: 'Yerini dəyiş və ya sırala',
            indicatorNewTitle: 'Davam edir',
            indicatorSuccessTitle: 'Tamamlandı',
            indicatorErrorTitle: 'Yükləmə xətası',
            indicatorPausedTitle: 'Upload Paused',
            indicatorLoadingTitle:  'Yükləmə &hellip;'
        },
        previewZoomButtonTitles: {
            prev: 'Əvvəlki fayla bax',
            next: 'Növbəti fayla bax',
            rotate: 'Rotate 90 deg. clockwise',
            toggleheader: 'Başlığı dəyiş',
            fullscreen: 'Tam ekranı dəyiş',
            borderless: 'Bölmələrsiz rejimi dəyiş',
            close: 'Ətraflı baxışı bağla'
        }
    };
}));
