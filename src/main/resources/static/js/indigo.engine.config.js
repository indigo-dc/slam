var app = angular.module('indigo');

app.config(function ($engineProvider, SESSION) {
    /**
     * SPECIFYING SLA
     */
    var slaSpec = {
        documentJSON: {
            "states": {
                "documentType": "sla",
                "serviceType": "computing"
            },
            "metrics": {}
        },
        name: 'Computing SLA',
        list: {
            columns: [
                {name: '@index', type: 'link', caption: 'ID'},
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'}
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
                'caption': 'Computing SLA',
                entries: [
                    {name: 'metrics.teamId',  caption: 'organization'},
                ],
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('sla', '/sla', '/sla/:id', ['SignedSlaComp'], slaSpec);

    var slaSSpec = {
        documentJSON: {
            "states": {
                "documentType": "sla",
                "serviceType": "storage"
            },
            "metrics": {}
        },
        name: 'Storage SLA',
        list: {
            columns: [
                {name: '@index', type: 'link', caption: 'ID'},
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'}
            ],
            caption: 'Storage SLAs'
        },
        document: {
            steps: [
                {
                    name: 'GENERAL',
                    categories: 'mcStorage'
                }
            ],
            details: {
                'caption': 'Storage SLA',
                entries: [
                    {name: 'metrics.teamId',  caption: 'organization'},
                ],
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('slaS', '/slaS', '/slaS/:id', ['SignedSlaComp'], slaSSpec);


    $engineProvider.dashboard({url: '/dashboard', label: 'SLAs', activetab: 'dashboard'},
        [
            {
                queryId: 'SignedSlaComp',
                label: 'Binding Computing SLAs',
                documentModelId: 'sla',
                showCreateButton: false,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'SignedSlaStorage',
                label: 'Binding Storage SLAs',
                documentModelId: 'sla',
                showCreateButton: false,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'workingSla',
                label: 'Computing SLA Negotiations',
                documentModelId: 'sla',
                showCreateButton: true
            },
            {
                queryId: 'workingSlaS',
                label: 'Storage SLA Negotiations',
                documentModelId: 'slaS',
                showCreateButton: true
            },
            {
                queryId: 'rejectedSla',
                label: 'Rejected SLAs',
                documentModelId: 'sla',
                showCreateButton: false
            }
        ]);

    if (SESSION.roles.indexOf("ROLE_PROVIDER") != -1) {
        $engineProvider.dashboard({url: '/provider', label: 'SLAs', activetab: 'provider'},
            [
                {
                    queryId: 'AllSlasProvider',
                    label: 'Binding SLAs',
                    documentModelId: 'sla',
                    showCreateButton: false
                },
                {
                    queryId: 'inProgressSlasProvider',
                    label: 'SLA in Negotiations',
                    documentModelId: 'sla',
                    showCreateButton: false
                },
            ]);
    }
});