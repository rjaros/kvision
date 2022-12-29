/*!
 * FileInput Uzbek Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author CyanoFresh <cyanofresh@gmail.com>
 * @Modified by Doston Usmonov <doston1533@gmail.com> 20.09.2019
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

    $.fn.fileinputLocales.uz = {
        sizeUnits: ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'], 
        bitRateUnits: ['B/s', 'KB/s', 'MB/s', 'GB/s', 'TB/s', 'PB/s', 'EB/s', 'ZB/s', 'YB/s'],
        fileSingle: 'fayl',
        filePlural: 'fayllar',
        browseLabel: 'Tanlash &hellip;',
        removeLabel: 'O‘chirish',
        removeTitle: 'Tanlangan fayllarni tozalash',
        cancelLabel: 'Bekor qilish',
        cancelTitle: 'Joriy yuklab olishni bekor qilish',
        pauseLabel: 'To‘xtatish',
        pauseTitle: 'Davomli yuklashni to‘xtatib turish',
        uploadLabel: 'Yuklab olish',
        uploadTitle: 'Tanlangan fayllarni yuklash',
        msgNo: 'Yo‘q',
        msgNoFilesSelected: 'Hech qanday fayl tanlanmagan',
        msgPaused: 'To‘xtatildi',
        msgCancelled: 'Bekor qilindi',
        msgPlaceholder: '{files} tanlash ...',
        msgZoomModalHeading: 'Batafsil ko‘rib chiqish',
        msgFileRequired: 'Yuklash uchun faylni tanlashingiz kerak.',
        msgSizeTooSmall: 'Siz tanlagan fayl hajmi: "{name}" (<b>{size}</b>). Tanlangan fayl hajmi <b>{minSize}</b> dan katta bo‘lishi lozim. Ko‘rsatilgan hajmdan kattaroq fayl yuklashga urinib ko‘ring',
        msgSizeTooLarge: '"{name}" fayl (<b>{size}</b>) ruxsat etilgan maksimal yuklash hajm: <b>{maxSize}</b> dan katta. Kichikroq fayl yuklashga urinib ko‘ring!',
        msgFilesTooLess: 'Yuklash uchun kamida <b>{n}</b> {files} tanlashingiz kerak. Yuklashga qaytadan urinib ko‘ring!',
        msgFilesTooMany: 'Siz tanlagan fayllar miqdori : <b>({n})</b>, ruxsat berilgan maksimal miqdor: <b>{m}</b> tadan ortiq. Ko‘rsatilgan miqdordan kamroq fayl tanlab, yuklashga qaytadan urinib ko‘ring!',
        msgTotalFilesTooMany: 'You can upload a maximum of <b>{m}</b> files (<b>{n}</b> files detected).',
        msgFileNotFound: '"{name}" fayl topilmadi!',
        msgFileSecured: '"{name}" faylni o‘qishga xavfsizlik cheklovi ruxsat bermaydi.',
        msgFileNotReadable: '"{name}" fayl o‘qilmaydi.',
        msgFilePreviewAborted: '"{name}" Faylni oldindan ko‘rish jarayoni to‘xtatildi.',
        msgFilePreviewError: '"{name}" faylni o‘qish paytida xatolik yuz berdi.',
        msgInvalidFileName: '"{name}" fayl nomida noto‘g‘ri yoki qo‘llab quvvatlanmaydigan belgilar mavjud.',
        msgInvalidFileType: '"{name}" fayl uchun yaroqsiz tur. Faqat "{types}" fayllari qo‘llab-quvvatlanadi.',
        msgInvalidFileExtension: '"{name}" fayl uchun noto‘g‘ri kengaytma. Faqat "{extensions}" fayllari qo‘llab-quvvatlanadi.',
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
        msgUploadAborted: 'Fayl yuklanishi bekor qilindi',
        msgUploadThreshold: 'Qayta ishlanmoqda &hellip;',
        msgUploadBegin: 'Ishga tushirilmoqda &hellip;',
        msgUploadEnd: 'Bajarildi',
        msgUploadResume: 'Yuklash davom etmoqda &hellip;',
        msgUploadEmpty: 'Yuklash uchun tegishli ma‘lumotlar mavjud emas.',
        msgUploadError: 'Yuklashda xato',
        msgDeleteError: 'Xatolikni o‘chirish',
        msgProgressError: 'Xato',
        msgValidationError: 'Fayl yuklash xatosi',
        msgLoading: '{Files} dan {index} faylini yuklash &hellip;',
        msgProgress: '{Files} dan {index}{name} faylini yuklashi  - {percent}% tugallandi.',
        msgSelected: '{n} {files} tanlangan',
        msgProcessing: 'Processing ...',
        msgFoldersNotAllowed: 'Faqat tortib qo‘yiladon fayllar! {n} o‘tirilgan tashlangan papka(lar).',
        msgImageWidthSmall: '"{name}" fayl kengligi <b>{size} px</b> (detected <b>{dimension} px</b>) dan kam bo‘lmasligi lozim.',
        msgImageHeightSmall: '"{name}" fayl bo‘yi <b>{size} px</b> (detected <b>{dimension} px</b>) dan kam bo‘lmasligi lozim.',
        msgImageWidthLarge: '"{name}" fayl kengligi <b>{size} px</b> (detected <b>{dimension} px</b>) dan kam bo‘lishi lozim.',
        msgImageHeightLarge: '"{name}" fayl bo‘yi <b>{size} px</b> (detected <b>{dimension} px</b>) dan kam bo‘lishi lozim.',
        msgImageResizeError: 'Rasm o‘lchamini o‘zgartirib bo‘lmadi.',
        msgImageResizeException: 'Rasm hajmini o‘zgartirishda xato.<pre>{errors}</pre>',
        msgAjaxError: '{operation} amaliyotida xatolik yuz berdi. Iltimos keyinroq qayta urinib ko‘ring!',
        msgAjaxProgressError: '{operation} bajarilmadi',
        msgDuplicateFile: '"{name}" nomli "{size}" hajmdagi fayl oldin tanlangan. Boshqa faylni tanlashga urinib ko‘ring.',
        msgResumableUploadRetriesExceeded:  '<b>{file}</b> faylini yuklash uchun <b>{max}</b> marta urinish bekor qilindi! Xato tafsilotlari: <pre>{error}</pre>',
        msgPendingTime: '{time} qolgan',
        msgCalculatingTime: 'qolgan vaqtni hisoblash',
        ajaxOperations: {
            deleteThumb: 'faylni o‘chirish',
            uploadThumb: 'fayl yuklash',
            uploadBatch: 'barcha fayllarni yuklash',
            uploadExtra: 'form ma‘lumotlarini yuklash'
        },
        dropZoneTitle: 'Fayllarni bu yerga tortib qo‘ying &hellip;',
        dropZoneClickTitle: '<br>(yoki {files} tanlash uchun bosing)',
        fileActionSettings: {
            removeTitle: 'Faylni olib tashlash',
            uploadTitle: 'Faylni yuklash',
            uploadRetryTitle: 'Qayta yuklab olish',
            downloadTitle: 'Faylni yuklab olish',
            rotateTitle: 'Rotate 90 deg. clockwise',
            zoomTitle: 'Tafsilotlarni ko‘rish',
            dragTitle: 'Ko‘chirish / qayta tartiblash',
            indicatorNewTitle: 'Hali yuklanmagan',
            indicatorSuccessTitle: 'Yuklandi',
            indicatorErrorTitle: 'Yuklashda xato',
            indicatorPausedTitle: 'Yuklash to‘xtatildi',
            indicatorLoadingTitle:  'Yuklanmoqda &hellip;'
        },
        previewZoomButtonTitles: {
            prev: 'Oldingi faylni ko‘rish',
            next: 'Keyingi faylni ko‘rish',
            rotate: 'Rotate 90 deg. clockwise',
            toggleheader: 'Sarlavhani yashirish',
            fullscreen: 'To‘liq ekranga o‘tish',
            borderless: 'Chegarasiz rejimga o‘tish',
            close: 'Batafsil ko‘rishni yopish'
        }
    };
}));
