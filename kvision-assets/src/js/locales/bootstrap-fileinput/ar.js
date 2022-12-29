/*!
 * FileInput Arabic Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author Yasser Lotfy <y_l@live.com>
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

    $.fn.fileinputLocales['ar'] = {
        sizeUnits: ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'], 
        bitRateUnits: ['B/s', 'KB/s', 'MB/s', 'GB/s', 'TB/s', 'PB/s', 'EB/s', 'ZB/s', 'YB/s'],
        fileSingle: 'ملف',
        filePlural: 'ملفات',
        browseLabel: 'تصفح &hellip;',
        removeLabel: 'إزالة',
        removeTitle: 'إزالة الملفات المختارة',
        cancelLabel: 'إلغاء',
        cancelTitle: 'إنهاء الرفع الحالي',
        pauseLabel: 'إيقاف مؤقت',
        pauseTitle: 'إيقاف التحميل الجاري مؤقتاً',
        uploadLabel: 'رفع',
        uploadTitle: 'رفع الملفات المختارة',
        msgNo: 'لا',
        msgNoFilesSelected: '',
        msgPaused: 'متوقف مؤقتاً',
        msgCancelled: 'ألغيت',
        msgPlaceholder: 'إختر {files} ...',
        msgZoomModalHeading: 'معاينة تفصيلية',
        msgFileRequired: 'يجب عليك تحديد ملف للتحميل.',
        msgSizeTooSmall: 'الملف "{name}" (<b>{size}</b>) صغير جداً ويجب أن يكون أكبر من <b>{minSize}</b>.',
        msgSizeTooLarge: 'الملف "{name}" (<b>{size}</b>) تعدى الحد الأقصى المسموح للرفع <b>{maxSize}</b>.',
        msgFilesTooLess: 'يجب عليك اختيار <b>{n}</b> {files} على الأقل للرفع.',
        msgFilesTooMany: 'عدد الملفات المختارة للرفع <b>({n})</b> تعدت الحد الأقصى المسموح به لعدد <b>{m}</b>.',
        msgTotalFilesTooMany: 'يمكنك تحميل كحد أقصى <b>{m}</b> ملفات (<b>{n}</b> ملفات تم الكشف عنها).',
        msgFileNotFound: 'الملف "{name}" غير موجود!',
        msgFileSecured: 'قيود أمنية تمنع قراءة الملف "{name}".',
        msgFileNotReadable: 'الملف "{name}" غير قابل للقراءة.',
        msgFilePreviewAborted: 'تم إلغاء معاينة الملف "{name}".',
        msgFilePreviewError: 'حدث خطأ أثناء قراءة الملف "{name}".',
        msgInvalidFileName: 'أحرف غير صالحة أو غير مدعومة في اسم الملف "{name}".',
        msgInvalidFileType: 'نوعية غير صالحة للملف "{name}". فقط هذه النوعيات مدعومة "{types}".',
        msgInvalidFileExtension: 'امتداد غير صالح للملف "{name}". فقط هذه الملفات مدعومة "{extensions}".',
        msgFileTypes: {
            'image': 'صورة',
            'html': 'HTML',
            'text': 'نص',
            'video': 'فيديو',
            'audio': 'ملف صوتي',
            'flash': 'فلاش',
            'pdf': 'PDF',
            'object': 'كائن'
        },
        msgUploadAborted: 'تم إلغاء رفع الملف',
        msgUploadThreshold: 'جاري المعالجة &hellip;',
        msgUploadBegin: 'جاري التهيئة &hellip;',
        msgUploadEnd: 'تم',
        msgUploadResume: 'استئناف التحميل &hellip;',
        msgUploadEmpty: 'لا توجد بيانات متاحة للتحميل.',
        msgUploadError: 'خطأ في التحميل',
        msgDeleteError: 'خطأ حذف',
        msgProgressError: 'خطأ',
        msgValidationError: 'خطأ التحقق من صحة',
        msgLoading: 'تحميل ملف {index} من {files} &hellip;',
        msgProgress: 'تحميل ملف {index} من {files} - {name} - {percent}% منتهي.',
        msgSelected: '{n} {files} مختار(ة)',
        msgProcessing: 'Processing ...',
        msgFoldersNotAllowed: 'اسحب وأفلت الملفات فقط! تم تخطي {n} مجلد(ات).',
        msgImageWidthSmall: 'عرض ملف الصورة "{name}" يجب أن يكون على الأقل <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageHeightSmall: 'طول ملف الصورة "{name}" يجب أن يكون على الأقل <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageWidthLarge: 'عرض ملف الصورة "{name}" لا يمكن أن يتعدى <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageHeightLarge: 'طول ملف الصورة "{name}" لا يمكن أن يتعدى <b>{size} px</b> (detected <b>{dimension} px</b>).',
        msgImageResizeError: 'لم يتمكن من معرفة أبعاد الصورة لتغييرها.',
        msgImageResizeException: 'حدث خطأ أثناء تغيير أبعاد الصورة.<pre>{errors}</pre>',
        msgAjaxError: 'حدث خطأ ما في العملية {operation} . الرجاء معاودة المحاولة في وقت لاحق!',
        msgAjaxProgressError: '{operation} فشلت',
        msgDuplicateFile: 'الملف "{name}" من نفس الحجم "{size}" ك ب" تم بالفعل اختياره في وقت سابق. تخطى التحديد المكرر.',
        msgResumableUploadRetriesExceeded:  'تم إحباط التحميل بعد <b>{max}</b> محاولات للملف <b>{file}</b>! تفاصيل الخطأ: <pre>{error}</pre>',
        msgPendingTime: '{time} متبقي',
        msgCalculatingTime: 'حساب الوقت المتبقي',
        ajaxOperations: {
            deleteThumb: 'ملف حذف',
            uploadThumb: 'ملف تحميل',
            uploadBatch: 'تحميل ملف دفعة واحدة',
            uploadExtra: 'تحميل بيانات نموذج'
        },
        dropZoneTitle: 'اسحب وأفلت الملفات هنا &hellip;',
        dropZoneClickTitle: '<br>(أو انقر لتحديد {files})',
        fileActionSettings: {
            removeTitle: 'إزالة الملف',
            uploadTitle: 'رفع الملف',
            uploadRetryTitle: 'إعادة محاولة التحميل',
            downloadTitle: 'تنزيل الملف',
            rotateTitle: 'Rotate 90 deg. clockwise',
            zoomTitle: 'مشاهدة التفاصيل',
            dragTitle: 'نقل / إعادة ترتيب',
            indicatorNewTitle: 'لم يتم الرفع بعد',
            indicatorSuccessTitle: 'تم الرفع',
            indicatorErrorTitle: 'خطأ بالرفع',
            indicatorPausedTitle: 'توقف التحميل مؤقتاً',
            indicatorLoadingTitle:  'جارٍ الرفع &hellip;'
        },
        previewZoomButtonTitles: {
            prev: 'عرض الملف السابق',
            next: 'عرض الملف التالي',
            rotate: 'Rotate 90 deg. clockwise',
            toggleheader: 'تبديل الرأسية',
            fullscreen: 'تبديل ملء الشاشة',
            borderless: 'تبديل وضع بلا حدود',
            close: 'إغلاق المعاينة التفصيلية'
        }
    };
}));
