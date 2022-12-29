/*!
 * FileInput Persian Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author Milad Nekofar <milad@nekofar.com>
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

    $.fn.fileinputLocales['fa'] = {
        sizeUnits: ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'], 
        bitRateUnits: ['B/s', 'KB/s', 'MB/s', 'GB/s', 'TB/s', 'PB/s', 'EB/s', 'ZB/s', 'YB/s'],
        fileSingle: 'فایل',
        filePlural: 'فایل‌ها',
        browseLabel: 'مرور &hellip;',
        removeLabel: 'حذف',
        removeTitle: 'پاکسازی فایل‌های انتخاب شده',
        cancelLabel: 'لغو',
        cancelTitle: 'لغو بارگزاری جاری',
        pauseLabel: 'Pause',
        pauseTitle: 'Pause ongoing upload',
        uploadLabel: 'بارگذاری',
        uploadTitle: 'بارگذاری فایل‌های انتخاب شده',
        msgNo: 'نه',
        msgNoFilesSelected: 'هیچ فایلی انتخاب نشده است',
        msgPaused: 'Paused',
        msgCancelled: 'لغو شد',
        msgPlaceholder: 'انتخاب {files} ...',
        msgZoomModalHeading: 'نمایش با جزییات',
        msgFileRequired: 'شما باید یک فایل برای بارگذاری انتخاب نمایید.',
        msgSizeTooSmall: 'فایل "{name}" (<b>{size}</b>) خیلی کوچک است و باید از <b>{minSize}</b>.',
        msgSizeTooLarge: 'فایل "{name}" (<b>{size}</b>) از حداکثر مجاز <b>{maxSize}</b> بزرگتر است.',
        msgFilesTooLess: 'شما باید حداقل <b>{n}</b> {files} فایل برای بارگذاری انتخاب کنید.',
        msgFilesTooMany: 'تعداد فایل‌های انتخاب شده برای بارگذاری <b>({n})</b> از حداکثر مجاز عبور کرده است <b>{m}</b>.',
        msgTotalFilesTooMany: 'You can upload a maximum of <b>{m}</b> files (<b>{n}</b> files detected).',
        msgFileNotFound: 'فایل "{name}" یافت نشد!',
        msgFileSecured: 'محدودیت های امنیتی مانع خواندن فایل "{name}" است.',
        msgFileNotReadable: 'فایل "{name}" قابل نوشتن نیست.',
        msgFilePreviewAborted: 'پیش نمایش فایل "{name}". به مشکل خورد',
        msgFilePreviewError: 'در هنگام خواندن فایل "{name}" خطایی رخ داد.',
        msgInvalidFileName: 'کاراکترهای غیرمجاز و یا ناشناخته در نام فایل "{name}".',
        msgInvalidFileType: 'نوع فایل "{name}" معتبر نیست. فقط "{types}" پشیبانی می‌شوند.',
        msgInvalidFileExtension: 'پسوند فایل "{name}" معتبر نیست. فقط "{extensions}" پشتیبانی می‌شوند.',
        msgFileTypes: {
            'image': 'عکس',
            'html': 'اچ تا ام ال',
            'text': 'متن',
            'video': 'ویدئو',
            'audio': 'صدا',
            'flash': 'فلش',
            'pdf': 'پی دی اف',
            'object': 'دیگر'
        },
        msgUploadAborted: 'بارگذاری فایل به مشکل خورد.',
        msgUploadThreshold: 'در حال پردازش &hellip;',
        msgUploadBegin: 'در حال شروع &hellip;',
        msgUploadEnd: 'انجام شد',
        msgUploadResume: 'Resuming upload &hellip;',
        msgUploadEmpty: 'هیچ داده معتبری برای بارگذاری موجود نیست.',
        msgUploadError: 'Upload Error',
        msgDeleteError: 'Delete Error',
        msgProgressError: 'Error',
        msgValidationError: 'خطای اعتبار سنجی',
        msgLoading: 'بارگیری فایل {index} از {files} &hellip;',
        msgProgress: 'بارگیری فایل {index} از {files} - {name} - {percent}% تمام شد.',
        msgSelected: '{n} {files} انتخاب شده',
        msgProcessing: 'Processing ...',
        msgFoldersNotAllowed: 'فقط فایل‌ها را بکشید و رها کنید! {n} پوشه نادیده گرفته شد.',
        msgImageWidthSmall: 'عرض فایل تصویر "{name}" باید حداقل {size} پیکسل باشد.',
        msgImageHeightSmall: 'ارتفاع فایل تصویر "{name}" باید حداقل {size} پیکسل باشد.',
        msgImageWidthLarge: 'عرض فایل تصویر "{name}" نمیتواند از {size} پیکسل بیشتر باشد.',
        msgImageHeightLarge: 'ارتفاع فایل تصویر "{name}" نمی‌تواند از {size} پیکسل بیشتر باشد.',
        msgImageResizeError: 'یافت نشد ابعاد تصویر را برای تغییر اندازه.',
        msgImageResizeException: 'خطا در هنگام تغییر اندازه تصویر.<pre>{errors}</pre>',
        msgAjaxError: 'به نظر مشکلی در حین {operation} روی داده است. لطفا دوباره تلاش کنید!',
        msgAjaxProgressError: '{operation} لغو شد',
        msgDuplicateFile: 'File "{name}" of same size "{size}" has already been selected earlier. Skipping duplicate selection.',
        msgResumableUploadRetriesExceeded:  'Upload aborted beyond <b>{max}</b> retries for file <b>{file}</b>! Error Details: <pre>{error}</pre>',
        msgPendingTime: '{time} remaining',
        msgCalculatingTime: 'calculating time remaining',
        ajaxOperations: {
            deleteThumb: 'حذف فایل',
            uploadThumb: 'بارگذاری فایل',
            uploadBatch: 'بارگذاری جمعی فایلها',
            uploadExtra: 'بارگذاری با کمک فُرم'
        },
        dropZoneTitle: 'فایل‌ها را بکشید و در اینجا رها کنید &hellip;',
        dropZoneClickTitle: '<br>(یا برای انتخاب {files} کلیک کنید)',
        fileActionSettings: {
            removeTitle: 'حذف فایل',
            uploadTitle: 'آپلود فایل',
            uploadRetryTitle: 'بارگیری مجدد',
            downloadTitle: 'دریافت فایل',
            rotateTitle: 'Rotate 90 deg. clockwise',
            zoomTitle: 'دیدن جزئیات',
            dragTitle: 'جابجایی / چیدمان',
            indicatorNewTitle: 'آپلود نشده است',
            indicatorSuccessTitle: 'آپلود شده',
            indicatorErrorTitle: 'بارگذاری خطا',
            indicatorPausedTitle: 'Upload Paused',
            indicatorLoadingTitle:  'آپلود &hellip;'
        },
        previewZoomButtonTitles: {
            prev: 'مشاهده فایل قبلی',
            next: 'مشاهده فایل بعدی',
            rotate: 'Rotate 90 deg. clockwise',
            toggleheader: 'نمایش عنوان',
            fullscreen: 'نمایش تمام صفحه',
            borderless: 'نمایش حاشیه',
            close: 'بستن نمایش با جزییات'
        }
    };
}));
