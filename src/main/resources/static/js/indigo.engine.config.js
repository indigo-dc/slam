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
        name: 'SLA',
        list: {
            columns: [
                // {name: '@index', type: 'link', caption: 'ID'},
                // {name: 'proposalName', caption: 'Title'},
                // {name: 'createdAt', type: 'date', caption: 'created'},
                // {name: 'beamlineChoice', caption: 'beamline'},
                // {name: 'states.documentState', caption: 'status'},
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
            details: {},
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('sla', '/sla', '/sla/:id', ['SignedSlaComp'], slaSpec);

    $engineProvider.dashboard({url: '/dashboard2', label: 'SLAs'},
        [{
            queryId: 'proposalMainProposer', label: 'Proposals', documentModelId: 'proposal', showCreateButton: true,
            customButtons: [{label: 'Upload Proposal', callback: 'uploadProposalModal'}],
            columns: [{name: 'id', type: 'link', caption: 'Proposal ID'},
                {name: 'proposalName', caption: 'Title'},
                {name: 'discipline', caption: 'Discipline'},
                {name: 'beamlineChoice', caption: 'Beamline'},
                {name: 'createdAt', type: 'date', caption: 'Created'},
                {name: 'states.documentState', caption: 'Status'},]
        },
            {
                queryId: 'proposalCoProposer',
                label: 'Proposals Co-Proposer',
                documentModelId: 'proposal',
                showCreateButton: false,
                columns: [{name: 'id', type: 'link', caption: 'Proposal ID'},
                    {name: 'proposalName', caption: 'Title'},
                    {name: '$ext.author.name', caption: 'Main Proposer'},
                    {name: 'discipline', caption: 'Discipline'},
                    {name: 'beamlineChoice', caption: 'Beamline'},
                    {name: 'createdAt', type: 'date', caption: 'Created'},
                    {name: 'states.documentState', caption: 'Status'},]
            },
        ]);
});