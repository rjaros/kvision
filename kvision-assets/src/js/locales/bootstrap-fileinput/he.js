/*!
 * FileInput Hebrew Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author Daniel Coryat <awq8002@gmail.com>
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

    $.fn.fileinputLocales['he'] = {
        sizeUnits: ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'], 
        bitRateUnits: ['B/s', 'KB/s', 'MB/s', 'GB/s', 'TB/s', 'PB/s', 'EB/s', 'ZB/s', 'YB/s'],
        fileSingle: 'קובץ',
        filePlural: 'קבצים',
        browseLabel: 'העלאה &hellip;',
        removeLabel: 'הסרה',
        removeTitle: 'נקה קבצים נבחרים',
        cancelLabel: 'ביטול',
        cancelTitle: 'ביטול העלאה מתמשכת',
        pauseLabel: 'Pause',
        pauseTitle: 'Pause ongoing upload',
        uploadLabel: 'טעינה',
        uploadTitle: 'טעינת קבצים נבחרים',
        msgNo: 'לא',
        msgNoFilesSelected: 'לא נבחרו קבצים',
        msgPaused: 'Paused',
        msgCancelled: 'מבוטל',
        msgPlaceholder: 'בחר {files} ...',
        msgZoomModalHeading: 'תצוגה מקדימה מפורטת',
        msgSizeTooSmall: 'קובץ "{name}" (<b>{size}</b>) קטן מדי וחייב להיות גדול מ <b>{minSize}</b>.',
        msgSizeTooLarge: 'קובץ "{name}" (<b>{size}</b>) חורג מהגודל המרבי המותר להעלאה של <b>{maxSize}</b>.',
        msgFilesTooLess: 'עליך לבחור לפחות <b>{n}</b> {files} להעלאה.',
        msgFilesTooMany: 'מספר הקבצים שנבחרו להעלאה <b>({n})</b> חורג מהמגבלה המרבית המותרת של <b>{m}</b>.',
        msgTotalFilesTooMany: 'You can upload a maximum of <b>{m}</b> files (<b>{n}</b> files detected).',
        msgFileNotFound: 'קובץ "{name}" לא נמצא!',
        msgFileSecured: 'הגבלות אבטחה מונעות קריאת הקובץ "{name}".',
        msgFileNotReadable: 'קובץ "{name}" לא קריא.',
        msgFilePreviewAborted: 'תצוגה מקדימה של הקובץ בוטלה עבור "{name}".',
        msgFilePreviewError: 'אירעה שגיאה בעת קריאת הקובץ "{name}".',
        msgInvalidFileName: 'תווים לא חוקיים או לא נתמכים בשם הקובץ "{name}".',
        msgInvalidFileType: 'סוג קובץ לא חוקי "{name}". רק "{types}" קבצים נתמכים.',
        msgInvalidFileExtension: 'תוסף לא חוקי עבור הקובץ "{name}". רק "{extensions}" קבצים נתמכים.',
        msgFileTypes: {
            'image': 'תמונה',
            'html': 'HTML',
            'text': 'טקסט',
            'video': 'וידאו',
            'audio': 'שמע',
            'flash': 'פלאש',
            'pdf': 'PDF',
            'object': 'אובייקט'
        },
        msgUploadAborted: 'העלאת הקובץ בוטלה',
        msgUploadThreshold: 'מעבד &hellip;',
        msgUploadBegin: 'מאתחל &hellip;',
        msgUploadEnd: 'בוצע',
        msgUploadResume: 'Resuming upload &hellip;',
        msgUploadEmpty: 'אין נתונים זמינים להעלאה.',
        msgValidationError: 'שגיאת אימות',
        msgLoading: 'טוען קובץ {index} של {files} &hellip;',
        msgProgress: 'טוען קובץ {index} של {files} - {name} - {percent}% הושלמה.',
        msgSelected: '{n} {files} נבחרו',
        msgProcessing: 'Processing ...',
        msgFoldersNotAllowed: 'גרירת קבצים ושחרורם בלבד! דילוג {n} גרירת תיקיה(s).',
        msgImageWidthSmall: 'רוחב קובץ התמונה "{name}" חייב להיות לפחות <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageHeightSmall: 'גובה קובץ התמונה "{name}" חייב להיות לפחות <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageWidthLarge: 'רוחב קובץ התמונה "{name}" לא יעלה על <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageHeightLarge: 'גובה קובץ התמונה "{name}" לא יעלה על <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageResizeError: 'לא ניתן לשנות את גודל מידות התמונה.',
        msgImageResizeException: 'שגיאה בעת שינוי גודל התמונה.<pre>{errors}</pre>',
        msgAjaxError: 'משהו השתבש עם {operation} המערכת. יש לנסות מאוחר יותר!',
        msgAjaxProgressError: '{operation} נכשל',
        msgDuplicateFile: 'File "{name}" of same size "{size}" has already been selected earlier. Skipping duplicate selection.',
        msgResumableUploadRetriesExceeded:  'Upload aborted beyond <b>{max}</b> retries for file <b>{file}</b>! Error Details: <pre>{error}</pre>',
        msgPendingTime: '{time} remaining',
        msgCalculatingTime: 'calculating time remaining',
        ajaxOperations: {
            deleteThumb: 'קובץ נמחק',
            uploadThumb: 'קובץ הועלה',
            uploadBatch: 'קובץ אצווה הועלה',
            uploadExtra: 'העלאת נתונים בטופס'
        },
        dropZoneTitle: 'גרירת קבצים ושחרורם כאן &hellip;',
        dropZoneClickTitle: '<br>(או לחץ /י כדי לבחור {files})',
        fileActionSettings: {
            removeTitle: 'הסרת קובץ',
            uploadTitle: 'טעינת קובץ',
            rotateTitle: 'Rotate 90 deg. clockwise',
            zoomTitle: 'הצגת פרטים',
            dragTitle: 'העברה / סידור מחדש',
            indicatorNewTitle: 'עדיין לא הועלה',
            indicatorSuccessTitle: 'הועלה',
            indicatorErrorTitle: 'שגיאת העלאה',
            indicatorPausedTitle: 'Upload Paused',
            indicatorLoadingTitle:  'מעלה &hellip;'
        },
        previewZoomButtonTitles: {
            prev: 'הצגת את הקובץ הקודם',
            next: 'הצגת את הקובץ הבא',
            rotate: 'Rotate 90 deg. clockwise',
            toggleheader: 'שינוי כותרת',
            fullscreen: 'מעבר למסך מלא',
            borderless: 'שינוי המודל ללא שוליים',
            close: 'סגירת תצוגה מקדימה מפורטת'
        }
    };
}));
