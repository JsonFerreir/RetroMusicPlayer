package cat.dam.yeison.retromusic.activities.bugreport.model

import cat.dam.yeison.retromusic.activities.bugreport.model.github.ExtraInfo

class Report(
    val title: String,
    private val description: String,
    private val deviceInfo: DeviceInfo?,
    private val extraInfo: ExtraInfo
) {
    fun getDescription(): String {
        return """
            $description
            
            -
            
            ${deviceInfo?.toMarkdown()}
            
            ${extraInfo.toMarkdown()}
            """.trimIndent()
    }
}