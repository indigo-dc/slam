angular.module('indigo')
.factory('setDocumentsWeights', function ($http) {
    var DOCUMENTS_WEIGHTS_URL = '/document-weights';

    return function (document_entries) {
        var out = [];
        angular.forEach(document_entries, function (documentEntry) {
            var document = documentEntry.document;
            out.push({document: document.id, weight: document.weight});
        });

        $http.post(DOCUMENTS_WEIGHTS_URL, out).then(function (response) {
            console.log('weights updated', out)
        })
    }
});