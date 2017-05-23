var app = angular.module('indigo');

app.config(function ($engineProvider) {
    /**
     * SPECIFYING SLA
     */
    var slaSpec = {
        documentJSON: {
            "states": {
                "documentType": "sla"
            },
            "metrics": {}
        },
        name: 'sla',
        list: {
            columns: [
                {name: '@index', type: 'link', caption: 'ID'},
                {name: 'name', caption: 'Name'},
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'},
                // {name: 'discipline', caption: 'disciplne'},
                // {name: 'proposalEvaluation', condition: "states.documentState == 'evaluated"}
            ],
            caption: 'SLAs'
        },
        document: {
            steps: [
                {
                    name: 'GENERAL',
                    categories: 'mcComputing'
                }
            ],
            details: {
                'caption': 'SLA'
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('sla', '/sla', '/sla/:id', ['SignedSlaComp'], slaSpec);

    $engineProvider.dashboard({url: '/dashboard', label: 'SLAs'},
        [
            {
                queryId: 'SignedSlaComp',
                label: 'My signed computing SLAs',
                documentModelId: 'sla',
                showCreateButton: false
            },
            {
                queryId: 'workingSla',
                label: 'SLAs in progrees',
                documentModelId: 'sla',
                showCreateButton: true,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'rejectedSla',
                label: 'Rejected SLAs',
                documentModelId: 'sla',
                showCreateButton: false
            },
        ]);
});