package com.template.contract

import com.template.state.IOUState
import net.corda.core.contracts.*
import net.corda.core.crypto.SecureHash

open class IOUContract : Contract {
    override fun verify(tx: TransactionForContract) {
        val command = tx.commands.requireSingleCommand<Create>()

        requireThat {
            // Constraints on the shape of the transaction.
            "No inputs should be consumed when issuing an IOU." using (tx.inputs.isEmpty())
            "Only one output state should be created." using (tx.outputs.size == 1)

            // IOU-specific constraints.
            val out = tx.outputs.single() as IOUState
            "The IOU's value must be non-negative." using (out.value > 0)
            "The sender and the recipient cannot be the same entity." using (out.sender != out.recipient)

            // Constraints on the signers.
            "All of the participants must be signers." using (command.signers.toSet() == out.participants.map { it.owningKey }.toSet())
        }
    }

    class Create : CommandData

    override val legalContractReference: SecureHash = SecureHash.sha256("Prose contract.")
}
