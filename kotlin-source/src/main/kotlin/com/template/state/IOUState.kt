package com.template.state

import com.template.contract.TemplateContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

class IOUState(val value: Int, val sender: Party, val recipient: Party,
               override val contract: TemplateContract) : ContractState {
    override val participants: List<AbstractParty>
        get() = listOf(sender, recipient)
}