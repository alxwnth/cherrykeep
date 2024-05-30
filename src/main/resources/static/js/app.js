import '../scss/main.scss';

import 'htmx.org';

document.body.addEventListener("htmx:configRequest", function (evt) {
    if (evt.detail.verb !== "get") {
        const csrfHeaderName = document
            .querySelector("meta[name='_csrf_header']")
            .getAttribute("content");
        evt.detail.headers[csrfHeaderName] = document
            .querySelector("meta[name='_csrf']")
            .getAttribute("content");
    }
});