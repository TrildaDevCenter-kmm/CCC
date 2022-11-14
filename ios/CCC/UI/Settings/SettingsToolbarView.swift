//
//  SettingsToolbarView.swift
//  CCC
//
//  Created by Mustafa Ozhan on 23.04.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Res

struct SettingsToolbarView: View {
    var backEvent: () -> Void

    var body: some View {
        HStack {
            ToolbarButton(clickEvent: backEvent, imgName: "chevron.left")

            Text(MR.strings().txt_settings.get())
                .font(relative: .title3)

            Spacer()
        }.padding(top: 20, leading: 10, bottom: 5, trailing: 20)
    }
}
