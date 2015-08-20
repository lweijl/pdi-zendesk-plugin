/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.zendesk;

import org.apache.commons.collections4.map.AbstractLinkedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.pentaho.di.core.RowSet;
import org.pentaho.di.core.row.RowMetaInterface;
import org.zendesk.client.v2.model.Audit;

public class ZendeskInputTicketAuditData extends ZendeskInputData {

  Long currentTicketId;
  AbstractLinkedMap<Long, ZendeskTicketAuditHistory> auditSummaries;

  RowMetaInterface ticketOverviewOutputRowMeta;
  RowMetaInterface ticketCommentsOutputRowMeta;
  RowMetaInterface ticketCustomFieldsOutputRowMeta;

  RowSet ticketOverviewOutputRowSet;
  RowSet ticketCommentsOutputRowSet;
  RowSet ticketCustomFieldsOutputRowSet;

  private boolean isNewTicket( Long newTicketId ) {
    if ( currentTicketId == null ) {
      return true;
    }
    return currentTicketId.equals( newTicketId );
  }

  private void newTicket( Long newTicketId ) {
    if ( isNewTicket( newTicketId ) ) {
      currentTicketId = newTicketId;
      auditSummaries = new LinkedMap<Long, ZendeskTicketAuditHistory>();
    }
  }

  void addAudit( Audit audit ) {
    if ( auditSummaries == null ) {
      newTicket( audit.getTicketId() );
    }
    if ( auditSummaries.size() <= 0 ) {
      auditSummaries.put( audit.getId(), ZendeskTicketAuditHistory.createFirstAudit( audit ) );
    } else {
      try {
        ZendeskTicketAuditHistory newAudit =
          auditSummaries.get( auditSummaries.lastKey() ).createNextAudit( audit, auditSummaries );
        auditSummaries.put( audit.getId(), newAudit );
      } catch ( CloneNotSupportedException e ) {
        e.printStackTrace();
      }
    }
  }


}
